package com.jason.web.lock;

import com.jason.web.constant.CommonConstant;
import com.jason.web.exception.LockException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * redisson分布式锁实现，基本锁功能的抽象实现
 * 本接口能满足绝大部分的需求，高级的锁功能，请自行扩展或直接使用原生api
 */
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty(prefix = "lock", name = "type", havingValue = "redis")
@Configuration
public class RedissonDistributedLock implements DistributedLock {
    @Autowired
    private RedissonClient redisson;

    private RLock getLock(String key, boolean isFair) {
        if (isFair) {
            return redisson.getFairLock(CommonConstant.LOCK_KEY_PREFIX + key);
        }
        return redisson.getLock(CommonConstant.LOCK_KEY_PREFIX + key);
    }

    @Override
    public RLock lock(String key, long leaseTime, TimeUnit unit, boolean isFair) {
        RLock lock = getLock(key, isFair);
        lock.lock(leaseTime, unit);
        return lock;
    }
    @Override
    public RLock lock(String key, long leaseTime, TimeUnit unit) {
        return lock(key, leaseTime, unit, false);
    }
    @Override
    public RLock lock(String key, boolean isFair) {
        return lock(key, -1, null, isFair);
    }
    @Override
    public RLock lock(String key) {
        return lock(key, -1, null, false);
    }

    @Override
    public RLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair) throws InterruptedException {
        RLock lock = getLock(key, isFair);
        if (lock.tryLock(waitTime, leaseTime, unit)) {
            return lock;
        }
        return null;
    }
    @Override
    public RLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        return tryLock(key, waitTime, leaseTime, unit, false);
    }
    @Override
    public RLock tryLock(String key, long waitTime, TimeUnit unit, boolean isFair) throws InterruptedException {
        return tryLock(key, waitTime, -1, unit, isFair);
    }
    @Override
    public RLock tryLock(String key, long waitTime, TimeUnit unit) throws InterruptedException {
        return tryLock(key, waitTime, -1, unit, false);
    }

    @Override
    public void unlock(Object lock) {
        if (lock != null) {
            if (lock instanceof RLock) {
                RLock rLock = (RLock)lock;
                if (rLock.isLocked()) {
                    rLock.unlock();
                }
            } else {
                throw new LockException("requires RLock type");
            }
        }
    }
}
