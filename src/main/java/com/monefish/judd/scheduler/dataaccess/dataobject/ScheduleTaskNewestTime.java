package com.monefish.judd.scheduler.dataaccess.dataobject;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ScheduleTaskNewestTime {
    private Timestamp newestCreateTime;
    private Timestamp newestUpdateTime;
}
