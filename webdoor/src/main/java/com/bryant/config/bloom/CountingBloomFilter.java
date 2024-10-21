package com.bryant.config.bloom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 计数型布隆过滤器
 * 计数型布隆过滤器通过引入计数器，支持元素的动态增删，适合需要频繁更新数据的场景。
 */
@Component
public class CountingBloomFilter {

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    private static final String BLOOM_FILTER_KEY = "bloom_filter";
    // 自定义哈希种子数组,数量越多，误报率越低，哈希函数越多
    private int[] hashSeeds = {7, 11, 13, 31, 37};

    // 插入元素
    public void add(Long productId) {
        HashOperations<String, String, Integer> hashOps =
                redisTemplate.opsForHash();

        for (int seed : hashSeeds) {
            // 哈希函数
            int hash = hash(productId, seed);
            String field = String.valueOf(hash);

//            hashOps.put(BLOOM_FILTER_KEY, field, 1);
            // 由于可能存在hash冲突，这里应该是+1，避免重复赋值
            hashOps.increment(BLOOM_FILTER_KEY, field, 1);
        }
    }

    // 查询元素是否存在
    public boolean mightContain(Long productId) {
        HashOperations<String, String, Integer> hashOps =
                redisTemplate.opsForHash();

        for (int seed : hashSeeds) {
            int hash = hash(productId, seed);
            Integer val = hashOps.get(BLOOM_FILTER_KEY, String.valueOf(hash));
            if (val == null || val.equals(0)) {
                return false;
            }
        }

        return true;
    }

    // 删除元素
    public void remove(Long productId) {
        HashOperations<String, String, Integer> hashOps = redisTemplate.opsForHash();
        for (int seed : hashSeeds) {
            int hash = hash(productId, seed);
            String field = String.valueOf(hash);
//            hashOps.delete(BLOOM_FILTER_KEY, hash);
            // 由于可能存在hash冲突，这里应该是-1，避免重复赋值
            hashOps.increment(BLOOM_FILTER_KEY, field, -1);
        }
    }

    // 简单哈希函数，使用种子计算
    private int hash(Long value, int seed) {
        return Math.abs((int) (value ^ seed) % 100000);
    }

}
