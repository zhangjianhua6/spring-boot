package com.jason.web.controller;

import com.jason.common.lock.Lock;
import com.jason.web.annotation.AuthIgnore;
import com.jason.web.pojo.response.AopResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁测试
 */
@Slf4j
@RestController
public class LockController {

    @Autowired
    private LockController lockController;

    @RequestMapping("tryLock")
    @AuthIgnore
    public AopResponse lock(String lockKey, String username){
        AopResponse response = new AopResponse();
        try {
            lockController.internalLock(lockKey, username);
        }catch (Exception ex){
            ex.printStackTrace();
            response.setCode("50");
            response.setMessage("获取分布式锁失败");
        }
        return response;
    }

    @Lock(key = "#lockKey", waitTime = 3)
    public void internalLock(String lockKey, String username) throws InterruptedException {
        log.info("{}获取分布式锁", username);
        TimeUnit.SECONDS.sleep(7);
        log.info("{}释放分布式锁", username);
    }


}
