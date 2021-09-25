package com.shuyuan.judd.scheduler.service;

import com.shuyuan.judd.scheduler.model.portal.ScheduleTaskQuery;
import com.shuyuan.judd.scheduler.model.portal.ScheduleTaskQueryResult;

public interface ScheduleTaskService {
    ScheduleTaskQueryResult query(ScheduleTaskQuery query);
}
