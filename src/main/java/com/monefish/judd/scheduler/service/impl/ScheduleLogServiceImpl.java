package com.monefish.judd.scheduler.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.monefish.judd.base.model.Response;
import com.monefish.judd.client.model.scheduler.TaskNotification;
import com.monefish.judd.scheduler.dataaccess.ScheduleLogDataAccess;
import com.monefish.judd.scheduler.dataaccess.ScheduleTaskDataAccess;
import com.monefish.judd.scheduler.dataaccess.dataobject.ScheduleLogDo;
import com.monefish.judd.scheduler.dataaccess.dataobject.ScheduleTaskDo;
import com.monefish.judd.scheduler.scheduler.SchedulerManager;
import com.monefish.judd.scheduler.service.ScheduleLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ScheduleLogServiceImpl implements ScheduleLogService {
    @Autowired
    private ScheduleLogDataAccess scheduleLogDataAccess;
    @Autowired
    private ScheduleTaskDataAccess scheduleTaskDataAccess;
    @Autowired
    private SchedulerManager schedulerManager;

    @Override
    public Response taskCompleted(TaskNotification notification) {
        ScheduleLogDo logDo = scheduleLogDataAccess.getByLogNo(notification.getLogNo());
        if (logDo == null) {
            log.error("taskCompleted，未找到调度记录， notification: {}", notification);
            return Response.createNativeFail("未找到指定的调度任务");
        } else {
            //可能存在潜在的乐观锁冲突，重试3次
            for (int i = 0; i < 3; i++) {
                String oldProgress = logDo.getProgress();
                JSONObject progress = JSONObject.parseObject(oldProgress);
                if (StringUtils.isNotEmpty(progress.getString(notification.composeTaskURI()))) {
                    progress.put(notification.composeTaskURI(), "Y");
                } else {
                    return Response.createNativeFail("未找到指定的调度任务的方法URI");
                }
                logDo.setProgress(progress.toJSONString());
                if (!progress.containsValue("N")) {
                    logDo.setCompleted('Y');
                }
                if (scheduleLogDataAccess.updateState(oldProgress, logDo) < 1) {
                    logDo = scheduleLogDataAccess.getByLogNo(notification.getLogNo());
                } else {
                    if ('Y' == logDo.getCompleted()) {
                        onTaskCompleted(logDo);
                    }
                    break;
                }
            }
        }
        return Response.createSuccess();
    }

    private void onTaskCompleted(ScheduleLogDo logDo) {
        List<ScheduleTaskDo> tasks = scheduleTaskDataAccess.getTaskWithPreTaskId(logDo.getTaskId());
        if (tasks != null && tasks.size() > 0) {
            for (ScheduleTaskDo task : tasks) {
                schedulerManager.executeTask(task, logDo.getLogNo());
            }
        }
    }
}
