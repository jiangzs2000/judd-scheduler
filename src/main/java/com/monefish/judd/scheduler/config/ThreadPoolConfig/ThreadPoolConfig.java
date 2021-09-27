package com.monefish.judd.scheduler.config.ThreadPoolConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author Sting
 * create 2018/11/30

 **/
@Configuration
@Data
@ConfigurationProperties(prefix = "scheduler.threadpoolconfig")
public class ThreadPoolConfig {
    //核心线程数
    private Integer corePoolSize;

    //最大线程数
    private Integer maxPoolSize;

    //线程池维护线程所允许的空闲时间
    private Integer keepAliveSeconds;

    //队列长度
    private Integer queueCapacity;

    //线程名称前缀
    private String threadNamePrefix;
}
