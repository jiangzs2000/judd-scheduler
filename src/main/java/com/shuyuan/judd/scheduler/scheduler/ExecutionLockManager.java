package com.shuyuan.judd.scheduler.scheduler;

import com.shuyuan.judd.scheduler.dataaccess.ExecutionLockDataAccess;
import com.shuyuan.judd.scheduler.dataaccess.ExecutionLockLogDataAccess;
import com.shuyuan.judd.scheduler.dataaccess.dataobject.ExecutionLockDo;
import com.shuyuan.judd.scheduler.dataaccess.dataobject.ExecutionLockLogDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;

@Component
@Slf4j
public class ExecutionLockManager {
    @Autowired
    private ExecutionLockDataAccess executionLockDataAccess;
    @Autowired
    private ExecutionLockLogDataAccess executionLockLogDataAccess;

    private String localAddress;

    @PostConstruct
    public void init() {
        try {
            localAddress = InetAddress.getLocalHost().getHostAddress();
            log.info("获取当前主机地址: {}", localAddress);
        } catch (UnknownHostException e) {
            log.error("获取当前主机地址失败");
            System.exit(1);
        }
    }

    /**
     * 竞争锁，并更新锁有效期
     */
    boolean competedLock(long expireTime) {
        ExecutionLockDo lock = executionLockDataAccess.getCurentLock();
        log.debug("lock: {}", lock);
        if (lock == null) {
            log.error("没有找到execution lock 记录，请尽快修复数据");
            return false;
        }
        if (lock.isExpired()) {
            String oldOwner = lock.getOwner();
            Timestamp oldExpireTime = lock.getExpireTime();
            lock.setOwner(localAddress);
            lock.setExpireTime(new Timestamp(System.currentTimeMillis() + expireTime));
            if (executionLockDataAccess.requireLock(oldOwner, oldExpireTime, lock) < 1) {
                log.info("没有竞争到执行锁，当前主机：{}", localAddress);
                return false;
            } else {
                log.info("竞争到了执行锁，当前主机：{}", localAddress);
                ExecutionLockLogDo lockLogDo = new ExecutionLockLogDo();
                lockLogDo.setExpireTime(oldExpireTime);
                lockLogDo.setOwner(oldOwner);
                executionLockLogDataAccess.insert(lockLogDo);
                return true;
            }
        } else {
            if (localAddress.equals(lock.getOwner())) {
                //更新锁有效期
                Timestamp oldExpireTime = lock.getExpireTime();
                lock.setExpireTime(new Timestamp(System.currentTimeMillis() + expireTime));
                if (executionLockDataAccess.requireLock(lock.getOwner(), oldExpireTime, lock) < 1) {
                    log.error("锁丢失了，当前主机：{}", localAddress);
                    return false;
                } else {
                    log.info("延续了执行锁，当前主机：{}", localAddress);
                    return true;
                }
            } else {
                log.info("已经有其他主机在执行任务，当前执行任务的主机： {}", lock.getOwner());
            }
        }
        return false;
    }
}
