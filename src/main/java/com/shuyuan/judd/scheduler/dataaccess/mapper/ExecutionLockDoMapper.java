package com.shuyuan.judd.scheduler.dataaccess.mapper;

import com.shuyuan.judd.scheduler.dataaccess.dataobject.ExecutionLockDo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface ExecutionLockDoMapper {

    ExecutionLockDo getCurentLock();

    int requireLock(@Param("oldOwner") String oldOwner, @Param("oldExpireTime")Timestamp oldExpireTime, @Param("lock") ExecutionLockDo lock);
}
