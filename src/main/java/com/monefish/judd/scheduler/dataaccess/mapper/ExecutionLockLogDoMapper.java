package com.monefish.judd.scheduler.dataaccess.mapper;

import com.monefish.judd.scheduler.dataaccess.dataobject.ExecutionLockLogDo;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionLockLogDoMapper {
    int insert(ExecutionLockLogDo executionLockLogDo);
}
