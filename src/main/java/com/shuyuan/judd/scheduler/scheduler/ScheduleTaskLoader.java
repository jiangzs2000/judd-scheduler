package com.shuyuan.judd.scheduler.scheduler;

import com.shuyuan.judd.scheduler.dataaccess.ScheduleTaskDataAccess;
import com.shuyuan.judd.scheduler.dataaccess.dataobject.ScheduleTaskDo;
import com.shuyuan.judd.scheduler.dataaccess.dataobject.ScheduleTaskNewestTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ScheduleTaskLoader {
    @Autowired
    private ScheduleTaskDataAccess scheduleTaskDataAccess;
    private ScheduleTaskNewestTime scheduleTaskNewestTime;

    public List<ScheduleTaskDo> loadUnloadedTasks() {
        if (scheduleTaskNewestTime == null) {
            scheduleTaskNewestTime = scheduleTaskDataAccess.getNewestTime();
            log.info("加载任务截至最新时间：{}", scheduleTaskNewestTime);
            return scheduleTaskDataAccess.getTaskWithCronPresent();
        } else {
            List<ScheduleTaskDo> l = scheduleTaskDataAccess.getTaskAfterTimeWithCronPresent(scheduleTaskNewestTime);
            scheduleTaskNewestTime = scheduleTaskDataAccess.getNewestTime();
            log.info("加载任务截至最新时间：{}", scheduleTaskNewestTime);
            return l;
        }
    }



}
