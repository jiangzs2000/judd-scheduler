package com.shuyuan.judd.scheduler.dataaccess.dataobject;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ExecutionLockLogDo {
    private Long id;
    private String owner;
    private Timestamp expireTime;
    private Timestamp createTime;
}

