package com.bryant.controller.redis;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Random;

@RestController
@RequestMapping("/redis/performance")
public class PerformanceController {

    @Resource(name = "userRedisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    private static final int _1MB = 1024 * 1024;


    private static final int _100MB = 1024 * 1024 * 100;


    @PostMapping("/write2Cache")
    public void write(
            @RequestParam(value = "id", defaultValue = "0") int id,
            @RequestParam(value = "isBigObject", defaultValue = "false") boolean isBigObject) {
        for (int i = id; i < 100; i++) {
            byte[] _1M = new byte[_100MB];
            BigObject bigObject = new BigObject(_1M);
            String key = "1M:" + i;
            if (isBigObject) {
                redisTemplate.opsForValue().set(key, bigObject);
            } else {
                redisTemplate.opsForValue().set(key, bigObject.toString());
            }
        }
    }

    @GetMapping("/readFromCache")
    public void read() {
        for (int i = 0; i < 100; i++) {
            redisTemplate.opsForValue().get("1M:" + i);
        }
    }

}

class BigObject implements Serializable {

    private static final long serialVersionUID = 589939600983368050L;

    private byte[] a;

    public BigObject(byte[] a) {
        this.a = a;
    }

    public byte[] getA() {
        return a;
    }

    public void setA(byte[] a) {
        this.a = a;
    }

}
