package com.shuyuan.judd.scheduler.dataaccess.mapper;

import com.shuyuan.judd.scheduler.dataaccess.dataobject.ExecutionLockLogDo;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionLockLogDoMapper {
    int insert(ExecutionLockLogDo executionLockLogDo);
}
