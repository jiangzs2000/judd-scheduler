package com.shuyuan.judd.scheduler.scheduler;

import lombok.Data;

@Data
public class SubTask {
    private String name;
    private String service;
    private String bean;
    private String method;
}
