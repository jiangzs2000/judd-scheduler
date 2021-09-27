package com.monefish.judd.scheduler.dataaccess.dataobject;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ScheduleLogDo {
    private String logNo;
    private Long taskId;
    private String preLogNo;
    private Timestamp createTime;
    private String progress;
    private Character completed;
    private Timestamp updateTime;
    private Integer status;
}
