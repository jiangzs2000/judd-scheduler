package com.shuyuan.judd.scheduler.dataaccess.impl;

import com.shuyuan.judd.scheduler.dataaccess.ExecutionLockLogDataAccess;
import com.shuyuan.judd.scheduler.dataaccess.dataobject.ExecutionLockLogDo;
import com.shuyuan.judd.scheduler.dataaccess.mapper.ExecutionLockLogDoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExecutionLockLogDataAccessImpl implements ExecutionLockLogDataAccess {
    @Autowired
    private ExecutionLockLogDoMapper executionLockLogDoMapper;
    @Override
    public int insert(ExecutionLockLogDo executionLockLogDo) {
        return executionLockLogDoMapper.insert(executionLockLogDo);
    }
}
