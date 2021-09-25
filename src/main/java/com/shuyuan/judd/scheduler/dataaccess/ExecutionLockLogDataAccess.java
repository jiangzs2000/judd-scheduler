package com.shuyuan.judd.scheduler.dataaccess;

import com.shuyuan.judd.scheduler.dataaccess.dataobject.ExecutionLockLogDo;

public interface ExecutionLockLogDataAccess {
    int insert(ExecutionLockLogDo executionLockLogDo);
}
