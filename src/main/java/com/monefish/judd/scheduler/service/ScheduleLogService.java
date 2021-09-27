package com.monefish.judd.scheduler.service;

import com.monefish.judd.base.model.Response;
import com.monefish.judd.client.model.scheduler.TaskNotification;

public interface ScheduleLogService {

    Response taskCompleted(TaskNotification notification);
}
