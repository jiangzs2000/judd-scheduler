package com.shuyuan.judd.scheduler.dataaccess.mapper;

import com.shuyuan.judd.scheduler.dataaccess.dataobject.ScheduleLogDo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleLogDoMapper {
    int insert(ScheduleLogDo scheduleLogDo);
    int update(ScheduleLogDo scheduleLogDo);
    int updateState(@Param("oldProgress") String oldProgress, @Param("scheduleLogDo") ScheduleLogDo scheduleLogDo);
    ScheduleLogDo getByLogNo(String logNo);


}
