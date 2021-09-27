package com.monefish.judd.scheduler.model.portal;

import com.monefish.judd.client.model.request.portal.QueryResult;
import com.monefish.judd.scheduler.dataaccess.dataobject.ScheduleTaskDo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScheduleTaskQueryResult extends QueryResult {
    private List<ScheduleTaskDo> list;
}
