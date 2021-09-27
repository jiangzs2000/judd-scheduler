package com.monefish.judd.scheduler.dataaccess;

import com.monefish.judd.scheduler.dataaccess.dataobject.ExecutionLockDo;

import java.sql.Timestamp;

public interface ExecutionLockDataAccess {
    ExecutionLockDo getCurentLock();
    int requireLock(String oldOwner, Timestamp oldExpireTime, ExecutionLockDo lock);
}
