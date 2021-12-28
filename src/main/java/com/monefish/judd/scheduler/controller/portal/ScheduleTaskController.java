package com.monefish.judd.scheduler.controller.portal;

import com.monefish.base.aops.ApiLog;
import com.monefish.base.model.Response;
import com.monefish.judd.scheduler.model.portal.ScheduleTaskQuery;
import com.monefish.judd.scheduler.model.portal.ScheduleTaskQueryResult;
import com.monefish.judd.scheduler.service.ScheduleTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portal")
@Api(tags = "门户接口：任务调度管理")
@Slf4j
public class ScheduleTaskController {
    @Autowired
    private ScheduleTaskService scheduleTaskService;

    @ApiOperation(value="查询任务列表")
    @PostMapping("/getScheduleTasksByQuery")
    @ApiLog
    public Response<ScheduleTaskQueryResult> getScheduleTasksByQuery(@RequestBody(required = false) ScheduleTaskQuery query) {
        return Response.createSuccess(scheduleTaskService.query(query));
    }
}
