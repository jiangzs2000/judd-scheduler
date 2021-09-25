package com.shuyuan.judd.scheduler.dataaccess;

import com.shuyuan.judd.scheduler.dataaccess.dataobject.ScheduleTaskDo;
import com.shuyuan.judd.scheduler.dataaccess.dataobject.ScheduleTaskNewestTime;
import com.shuyuan.judd.scheduler.model.portal.ScheduleTaskQuery;

import java.util.List;
import java.util.Map;

public interface ScheduleTaskDataAccess {
    int insert(ScheduleTaskDo scheduleTaskDo);
    int update(ScheduleTaskDo scheduleTaskDo);
    int remove(Long id);
    List<ScheduleTaskDo> getTaskWithCronPresent();
    List<ScheduleTaskDo> getTaskWithPreTaskId(Long preTaskId);
    List<ScheduleTaskDo> getByQuery(ScheduleTaskQuery query);
    Map<String , Object> getCountByQuery(ScheduleTaskQuery query);
    ScheduleTaskNewestTime getNewestTime();
    List<ScheduleTaskDo> getTaskAfterTimeWithCronPresent(ScheduleTaskNewestTime scheduleTaskNewestTime);
}
