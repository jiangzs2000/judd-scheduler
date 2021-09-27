package com.monefish.judd.scheduler.service;

import com.monefish.judd.scheduler.model.portal.ScheduleTaskQuery;
import com.monefish.judd.scheduler.model.portal.ScheduleTaskQueryResult;

public interface ScheduleTaskService {
    ScheduleTaskQueryResult query(ScheduleTaskQuery query);
}
