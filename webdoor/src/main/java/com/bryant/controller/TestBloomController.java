package com.bryant.controller;

import com.bryant.config.bloom.CountingBloomFilter;
import com.google.common.hash.BloomFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bloom")
@Slf4j
public class TestBloomController {

    @Autowired
    private BloomFilter bloomFilter;

    @Autowired
    private CountingBloomFilter countingBloomFilter;

    @GetMapping("/guava")
    public String guava(@RequestParam("id") Long id) {
        boolean result = bloomFilter.mightContain(1L);
        if (result) {
            // 执行数据库查询
            return "ok";
        }
        return "not ok";
    }

    @GetMapping("/custom")
    public String custom(@RequestParam("id") Long id)
    {
        boolean result = countingBloomFilter.mightContain(1L);
        if (result) {
            // 执行数据库查询
            return "ok";
        }
        return "not ok";
    }

}
