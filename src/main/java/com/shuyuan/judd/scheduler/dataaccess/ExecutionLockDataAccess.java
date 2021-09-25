package com.shuyuan.judd.scheduler.dataaccess;

import com.shuyuan.judd.scheduler.dataaccess.dataobject.ExecutionLockDo;

import java.sql.Timestamp;

public interface ExecutionLockDataAccess {
    ExecutionLockDo getCurentLock();
    int requireLock(String oldOwner, Timestamp oldExpireTime, ExecutionLockDo lock);
}
