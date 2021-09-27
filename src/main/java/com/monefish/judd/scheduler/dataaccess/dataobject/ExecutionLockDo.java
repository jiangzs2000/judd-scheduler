package com.monefish.judd.scheduler.dataaccess.dataobject;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ExecutionLockDo {
    private String owner;
    private Timestamp expireTime;
    private Timestamp updateTime;
    private boolean expired;
}
