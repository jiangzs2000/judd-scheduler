package com.monefish.judd.scheduler.dataaccess;

import com.monefish.judd.scheduler.dataaccess.dataobject.ExecutionLockLogDo;

public interface ExecutionLockLogDataAccess {
    int insert(ExecutionLockLogDo executionLockLogDo);
}
