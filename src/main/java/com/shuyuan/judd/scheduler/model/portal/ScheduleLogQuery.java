package com.shuyuan.judd.scheduler.model.portal;

import com.shuyuan.judd.client.model.request.portal.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScheduleLogQuery extends Query {
    private String merchantNo;
    private String startDate;
    private String endDate;
    private String search;
    private Long pid;
    private Long createUser;
}
