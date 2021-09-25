package com.shuyuan.judd.scheduler.dataaccess.impl;

import com.shuyuan.judd.scheduler.dataaccess.ScheduleTaskDataAccess;
import com.shuyuan.judd.scheduler.dataaccess.dataobject.ScheduleTaskDo;
import com.shuyuan.judd.scheduler.dataaccess.dataobject.ScheduleTaskNewestTime;
import com.shuyuan.judd.scheduler.dataaccess.mapper.ScheduleTaskDoMapper;
import com.shuyuan.judd.scheduler.model.portal.ScheduleTaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ScheduleTaskDataAccessImpl implements ScheduleTaskDataAccess {
    @Autowired
    private ScheduleTaskDoMapper scheduleTaskDoMapper;
    @Override
    public int insert(ScheduleTaskDo scheduleTaskDo) {
        return scheduleTaskDoMapper.insert(scheduleTaskDo);
    }

    @Override
    public int update(ScheduleTaskDo scheduleTaskDo) {
        return scheduleTaskDoMapper.update(scheduleTaskDo);
    }

    @Override
    public int remove(Long id) {
        return scheduleTaskDoMapper.remove(id);
    }

    @Override
    public List<ScheduleTaskDo> getTaskWithCronPresent() {
        return scheduleTaskDoMapper.getTaskWithCronPresent();
    }

    @Override
    public List<ScheduleTaskDo> getTaskWithPreTaskId(Long preTaskId) {
        return scheduleTaskDoMapper.getTaskWithPreTaskId(preTaskId);
    }

    @Override
    public List<ScheduleTaskDo> getByQuery(ScheduleTaskQuery query) {
        return scheduleTaskDoMapper.getByQuery(query);
    }

    @Override
    public Map<String, Object> getCountByQuery(ScheduleTaskQuery query) {
        return scheduleTaskDoMapper.getCountByQuery(query);
    }

    @Override
    public ScheduleTaskNewestTime getNewestTime() {
        return scheduleTaskDoMapper.getNewestTime();
    }

    @Override
    public List<ScheduleTaskDo> getTaskAfterTimeWithCronPresent(ScheduleTaskNewestTime scheduleTaskNewestTime) {
        return scheduleTaskDoMapper.getTaskAfterTimeWithCronPresent(scheduleTaskNewestTime);
    }
}
