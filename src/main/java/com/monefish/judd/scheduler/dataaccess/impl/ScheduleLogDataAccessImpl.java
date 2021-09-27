package com.monefish.judd.scheduler.dataaccess.impl;

import com.monefish.judd.scheduler.dataaccess.ScheduleLogDataAccess;
import com.monefish.judd.scheduler.dataaccess.dataobject.ScheduleLogDo;
import com.monefish.judd.scheduler.dataaccess.mapper.ScheduleLogDoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleLogDataAccessImpl implements ScheduleLogDataAccess {
    @Autowired
    private ScheduleLogDoMapper scheduleLogDoMapper;
    @Override
    public int insert(ScheduleLogDo scheduleLogDo) {
        return scheduleLogDoMapper.insert(scheduleLogDo);
    }

    @Override
    public int update(ScheduleLogDo scheduleLogDo) {
        return scheduleLogDoMapper.update(scheduleLogDo);
    }

    @Override
    public int updateState(String oldProgress, ScheduleLogDo scheduleLogDo) {
        return scheduleLogDoMapper.updateState(oldProgress, scheduleLogDo);
    }

    @Override
    public ScheduleLogDo getByLogNo(String logNo) {
        return scheduleLogDoMapper.getByLogNo(logNo);
    }
}
