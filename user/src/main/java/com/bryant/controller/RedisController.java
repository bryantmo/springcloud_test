package com.bryant.controller;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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

    /**
     * 获取锁是有限阻塞的
     * @param key
     * @return
     */
    @GetMapping("/tryLock")
    public Boolean getLock(
            @RequestParam(value = "key") String key
    ){
        RLock lock = redissonClient.getLock(key);
        try {
            boolean tryLock = lock.tryLock(100, TimeUnit.SECONDS);
//            lock.tryLock(100, 1, TimeUnit.SECONDS);
            return tryLock;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("try lock fail, key is {}", key);
            return Boolean.FALSE;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取锁是同步阻塞的
     * @param key
     * @throws InterruptedException
     */
    @PostMapping("/lock")
    public void lock(
            @RequestParam(value = "key") String key
    ) throws InterruptedException {
        RLock lock = redissonClient.getLock(key);
        // 锁10s
        lock.lock(3, TimeUnit.SECONDS);
        // 休眠40s（调用另外接口getLock，尝试是否获取到锁，答案是不能获取到）
        Thread.sleep(1000L);
        // 释放锁
        lock.unlock();
    }

}
