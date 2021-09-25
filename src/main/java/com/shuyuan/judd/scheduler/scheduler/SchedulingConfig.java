package com.shuyuan.judd.scheduler.scheduler;

import com.shuyuan.judd.scheduler.config.ThreadPoolConfig.ThreadPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class SchedulingConfig {
    @Bean
    public TaskScheduler taskScheduler(ThreadPoolConfig config) {
        ThreadPoolTaskScheduler taskScheduler =
                new ThreadPoolTaskScheduler(); // 定时任务执行线程池核心线程数
        taskScheduler.setPoolSize(config.getCorePoolSize());
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix(config.getThreadNamePrefix());
        return taskScheduler;
    }
}
