package com.shuyuan.judd.scheduler.model.portal;

import com.shuyuan.judd.client.model.request.portal.Query;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScheduleTaskQuery extends Query {
    private String startDate;
    private String endDate;
    private String search;
}
