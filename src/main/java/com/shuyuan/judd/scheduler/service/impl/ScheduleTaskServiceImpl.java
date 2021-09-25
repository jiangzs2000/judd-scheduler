package com.shuyuan.judd.scheduler.service.impl;

import com.shuyuan.judd.base.model.Response;
import com.shuyuan.judd.scheduler.dataaccess.ScheduleTaskDataAccess;
import com.shuyuan.judd.scheduler.dataaccess.dataobject.ScheduleTaskDo;
import com.shuyuan.judd.scheduler.model.portal.ScheduleTaskQuery;
import com.shuyuan.judd.scheduler.model.portal.ScheduleTaskQueryResult;
import com.shuyuan.judd.scheduler.service.ScheduleTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScheduleTaskServiceImpl implements ScheduleTaskService {
    @Autowired
    private ScheduleTaskDataAccess scheduleTaskDataAccess;
    @Override
    public ScheduleTaskQueryResult query(ScheduleTaskQuery query) {
        ScheduleTaskQueryResult res = new ScheduleTaskQueryResult();
        if(query.getPageSize() == null){ //pagination is not needed
            List<ScheduleTaskDo> list = scheduleTaskDataAccess.getByQuery(query);
            res.setList(list);
            return res;
        } else {
            if (query.getPage() == null) {
                res.setPage(1);
                query.setPage(0);//default is the first page
            } else {
                res.setPage(query.getPage());
                query.setPage((query.getPage() - 1)*query.getPageSize());
            }
            List<ScheduleTaskDo> list = scheduleTaskDataAccess.getByQuery(query);
            res.setList(list);
            Map<String , Object> map = scheduleTaskDataAccess.getCountByQuery(query);
            long count = (Long) map.get("count");
            res.setCount(count);
            res.setTotalPages(new Double(Math.ceil(((double) count)/query.getPageSize())).intValue());
            return res;
        }
    }
}
