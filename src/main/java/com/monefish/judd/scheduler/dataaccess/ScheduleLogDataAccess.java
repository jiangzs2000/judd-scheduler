package com.monefish.judd.scheduler.dataaccess;

import com.monefish.judd.scheduler.dataaccess.dataobject.ScheduleLogDo;

public interface ScheduleLogDataAccess {
    int insert(ScheduleLogDo scheduleLogDo);
    int update(ScheduleLogDo scheduleLogDo);
    int updateState(String oldProgress, ScheduleLogDo scheduleLogDo);
    ScheduleLogDo getByLogNo(String logNo);
}
