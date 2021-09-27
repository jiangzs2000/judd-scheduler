package com.monefish.judd.scheduler.scheduler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.monefish.judd.client.model.scheduler.TaskNotification;
import com.monefish.judd.scheduler.dataaccess.ScheduleLogDataAccess;
import com.monefish.judd.scheduler.dataaccess.dataobject.ScheduleLogDo;
import com.monefish.judd.scheduler.dataaccess.dataobject.ScheduleTaskDo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Lazy(value = false)
@Component
@Slf4j
public class SchedulerManager implements SchedulingConfigurer {

    @Autowired
    private ScheduleTaskLoader scheduleTaskLoader;
    @Autowired
    private ScheduleLogDataAccess scheduleLogDataAccess;
    @Resource(name = "taskScheduler")
    private TaskScheduler taskScheduler;
    @Autowired
    private ExecutionLockManager executionLockManager;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${scheduler.executionLockExpireTime}")
    private Long executionLockExpireTime; //秒
    private ScheduledTaskRegistrar scheduledTaskRegistrar;
    private boolean taskLoaded = false;
    private Map<Long, ScheduledTask> taskMap = new ConcurrentHashMap<>();
    @Value("${scheduler.refresherCron}")
    private String refresherCron;
    // taskRefresher跟其它数据库配置的任务一样，只有在抢到执行锁后才能执行，如果锁丢了，需要卸载
    protected ScheduledTask taskRefresher;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        log.info("开始加载并初始化任务调度");
        this.scheduledTaskRegistrar = scheduledTaskRegistrar;
        scheduledTaskRegistrar.setScheduler(taskScheduler);
        scheduledTaskRegistrar.addFixedRateTask(() -> {
            boolean competed = executionLockManager.competedLock(executionLockExpireTime);
            if (competed) {
                if (taskLoaded) {
                    log.info("执行锁延续成功。");
                } else {
                    loadTasks();
                }
            } else {
                if (taskLoaded) {
                    log.error("执行锁莫名丢失！！,开始卸载任务");
                    unloadTasks();
                }
            }
        }, executionLockExpireTime /2);
    }

    private void loadTasks() {
        List<ScheduleTaskDo> l = scheduleTaskLoader.loadUnloadedTasks();
        if (l == null || l.size() == 0) {
            return;
        }
        for (ScheduleTaskDo task : l) {
            log.info("加载task: {}", task);
            taskMap.put(task.getId(), scheduledTaskRegistrar.scheduleCronTask(new CronTask(getTaskExecutor(task), task.getCron())));
        }
        taskRefresher = scheduledTaskRegistrar.scheduleCronTask(new CronTask(getTaskRefresher(), refresherCron));
        taskLoaded = true;
    }

    private void unloadTasks() {
        if (taskMap.size() > 0) {
            for (ScheduledTask task : taskMap.values()) {
                task.cancel();
            }
            taskMap.clear();
        }
        taskRefresher.cancel();
        taskRefresher = null;
        taskLoaded = false;
    }

    private Runnable getTaskExecutor(ScheduleTaskDo task) {
        log.info("准备任务执行器，task: {} ", task);
        //return () -> log.info("执行task: {}", task);
        return () -> executeTask(task);
    }

    private void executeTask(ScheduleTaskDo task) {
        executeTask(task, null);
    }

    public void executeTask(ScheduleTaskDo task, String preLogNo) {
        try {
            if (StringUtils.isNotBlank(task.getSubTask())) {
                List<SubTask> subTasks = JSONArray.parseArray(task.getSubTask(), SubTask.class);
                if (subTasks != null && subTasks.size() > 0) {
                    ScheduleLogDo logDo = new ScheduleLogDo();
                    logDo.setLogNo(UUID.randomUUID().toString().replace("-", ""));
                    logDo.setTaskId(task.getId());
                    logDo.setPreLogNo(preLogNo);
                    logDo.setCompleted('N');
                    JSONObject progress = new JSONObject();
                    for (SubTask subTask : subTasks) {
                        TaskNotification tn = new TaskNotification();
                        BeanUtils.copyProperties(subTask, tn);
                        tn.setLogNo(logDo.getLogNo());
                        progress.put(tn.composeTaskURI(), "N");
                        Message msg = new Message(subTask.getService(), null, null, JSONObject.toJSONString(tn).getBytes(StandardCharsets.UTF_8));
                        rocketMQTemplate.asyncSend(subTask.getService(), msg, new SendCallback() {
                            @Override
                            public void onSuccess(SendResult sendResult) {
                                log.info("任务通知发送队列成功, subTask: {}", subTask);
                            }
                            @Override
                            public void onException(Throwable throwable) {
                                log.error("任务通知发送队列失败, subTask: {}", subTask);
                            }
                        });
                    }
                    logDo.setProgress(progress.toJSONString());
                    scheduleLogDataAccess.insert(logDo);
                }
            }
        } catch (Throwable t) {
            log.error("执行任务出错， task: {}", task, t);
        }
    }

    private Runnable getTaskRefresher() {
        return () -> {
          List<ScheduleTaskDo> tasks = scheduleTaskLoader.loadUnloadedTasks();
          log.info("执行任务刷新， tasks: {}", tasks);
          if (tasks != null && tasks.size() >0) {
              for(ScheduleTaskDo task : tasks) {
                  ScheduledTask curTask = taskMap.get(task.getId());
                  if (curTask != null) {
                      log.info("卸载task: {}", curTask);
                      curTask.cancel();
                  }
                  //增加新或新版任务
                  if (task.getStatus() == 1) {//不是删除操作，才把新版任务重新注册
                      log.info("加载task: {}", task);
                      taskMap.put(task.getId(), scheduledTaskRegistrar.scheduleCronTask(new CronTask(getTaskExecutor(task), task.getCron())));
                  }
              }
          }
        };
    }
}
