package com.shuyuan.judd.scheduler.service;

import com.shuyuan.judd.base.model.Response;
import com.shuyuan.judd.client.model.scheduler.TaskNotification;

public interface ScheduleLogService {

    Response taskCompleted(TaskNotification notification);
}
