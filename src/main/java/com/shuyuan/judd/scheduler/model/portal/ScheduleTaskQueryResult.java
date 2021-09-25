package com.shuyuan.judd.scheduler.model.portal;

import com.shuyuan.judd.client.model.request.portal.QueryResult;
import com.shuyuan.judd.scheduler.dataaccess.dataobject.ScheduleTaskDo;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleTaskQueryResult extends QueryResult {
    private List<ScheduleTaskDo> list;
}
