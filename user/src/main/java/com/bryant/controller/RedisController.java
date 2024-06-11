package com.bryant.controller;

import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * set value to redis
     * @param key
     * @param value
     * @return
     */
    @PostMapping("/set")
    public String set(
            @RequestParam(value = "key") String key,
            @RequestParam(value = "value") String value
    ){
        if (redisTemplate.hasKey(key)) {
            return "set failed, key exists";
        }
        redisTemplate.opsForValue().set(key, value);
        return "set success";
    }

    /**
     * get value from redis
     * @return
     */
    @GetMapping("/get")
    public String get(
            @RequestParam(value = "key") String key
    ){
        String value = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(value)) {
            return "get failed, key not exists";
        }
        return String.format("get success, value is %s", value);
    }

    @GetMapping("/getLock")
    public int getLock(
            @RequestParam(value = "key") String key
    ){
        RLock lock = redissonClient.getLock(key);
        int holdCount = lock.getHoldCount();
        return holdCount;
    }

    @PostMapping("/lock")
    public void lock(
            @RequestParam(value = "key") String key
    ) throws InterruptedException {
        RLock lock = redissonClient.getLock(key);
        // 锁10s
        lock.lock(10, TimeUnit.SECONDS);
        // 休眠40s（调用另外接口getLock，尝试是否获取到锁）
        Thread.sleep(40000L);
        // 释放锁
        lock.unlock();
    }

}
