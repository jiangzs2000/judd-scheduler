package com.shuyuan.judd.scheduler.dataaccess.dataobject;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ScheduleTaskDo {
    private Long id;
    private Long preTaskId;
    private String taskName;
    private String cron;
    private String subTask;
    private Long createUser;
    private Timestamp createTime;
    private Long updateUser;
    private Timestamp updateTime;
    private Integer status;
}
