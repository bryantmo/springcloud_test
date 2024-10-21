package com.bryant.config.bloom;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 依赖guava底层的标准BloomFilter实现方案
 * 布隆过滤器和计数型布隆过滤器都是高效的集合查询工具，适合大规模数据场景。布隆过滤器适用于不需要删除操作的静态集合，空间利用率高
 * BloomFilter 构造器：
 * - Funnels.longFunnel()：指定布隆过滤器处理的数据类型为长整型（Long）。
 * - 10000000：预计插入的元素数量为1000万。
 * - 0.01：误判率设置为1%，即当查询一个元素时，有1%的概率会误判该元素存在于集合中。
 */
@Configuration
public class BloomFilterConfig {

    @Bean
    public BloomFilter<Long> productBloomFilter() {
        // 假设我们估计有1000万商品，期望误判率为0.01
        BloomFilter<Long> bloomFilter = BloomFilter.create(Funnels.longFunnel(), 10000000, 0.01);

        // 从数据库加载商品ID并初始化布隆过滤器
        List<Long> productIds = loadAllProductIdsFromDatabase();
        for (Long productId : productIds) {
            bloomFilter.put(productId);
        }

        return bloomFilter;
    }

    // 假设这是从数据库中加载所有商品ID的函数
    private List<Long> loadAllProductIdsFromDatabase() {
        // 具体实现略，假设从数据库中查询所有商品ID
        return new ArrayList<>();
    }

}
