package com.monefish.judd.scheduler.dataaccess.impl;

import com.monefish.judd.scheduler.dataaccess.ExecutionLockDataAccess;
import com.monefish.judd.scheduler.dataaccess.dataobject.ExecutionLockDo;
import com.monefish.judd.scheduler.dataaccess.mapper.ExecutionLockDoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class ExecutionLockDataAccessImpl implements ExecutionLockDataAccess {
    @Autowired
    private ExecutionLockDoMapper executionLockDoMapper;
    @Override
    public ExecutionLockDo getCurentLock() {
        return executionLockDoMapper.getCurentLock();
    }

    @Override
    public int requireLock(String oldOwner, Timestamp oldExpireTime,
                           ExecutionLockDo lock) {
        return executionLockDoMapper.requireLock(oldOwner, oldExpireTime, lock);
    }
}
