package com.shuyuan.judd.scheduler.controller;

import com.shuyuan.judd.base.aops.ApiLog;
import com.shuyuan.judd.base.model.Response;
import com.shuyuan.judd.client.model.scheduler.TaskNotification;
import com.shuyuan.judd.scheduler.service.ScheduleLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Api(tags = "内部接口：任务调度回调")
@Slf4j
public class SchedulerController {
    @Autowired
    private ScheduleLogService scheduleLogService;

    @ApiOperation(value = "通知任务完成")
    @PostMapping("/taskCompleted")
    @ApiLog
    public Response taskCompleted(@RequestBody TaskNotification notification) {
        return scheduleLogService.taskCompleted(notification);
    }

}
