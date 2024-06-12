package com.bryant.controller;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LuaController {

    private static final String luaScript = "local a = tonumber(ARGV[1])\nlocal b = tonumber(ARGV[2])\nreturn a + b";
    private static final String luaScript2 = "print(\"Hello World!\")";

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostMapping("/lua/script/zadd")
    public String zadd() {
        DefaultRedisScript<Boolean> script = new DefaultRedisScript<>();
        script.setResultType(Boolean.class);
        // 加载lua脚本
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/zset-add.lua")));

        double score = Double.valueOf(new Random().nextInt(1000));
        String member = "bryant" + new Random().nextInt(1000);

        Boolean result = stringRedisTemplate.execute(script, Collections.singletonList("redis key"), String.valueOf(score), member);
        return String.format("result is %s", result);
    }

    @PostMapping("/lua/zset_range")
    public String luaScript() {
        // 构建脚本，泛型类型是期待的返回值类型
        DefaultRedisScript<List> script = new DefaultRedisScript<>();
        // 加载lua脚本
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/zset-get-del.lua")));
        script.setResultType(List.class);

        // 第一个参数是脚本， 第二个参数是redis key列表， 第三个可变长度参数是脚本的参数，具体依据脚本而定
        List result = stringRedisTemplate.execute(script, Collections.singletonList("redis key"), "-10000", "10000");
        return String.format("result is %s", result);
    }

    @PostMapping("/lua/file")
    public String file() {
        Resource resource = resourceLoader.getResource("classpath:lua/myscript.lua");
        String luaScript;

        try {
            luaScript = resource.getFile().getAbsolutePath();
        } catch (Exception ex) {
            log.error("get lua file path fail, ex is {}", ex);
            return String.format("get lua file path fail");
        }

        RedisScript<Integer> script = new DefaultRedisScript<>(luaScript, Integer.class);
        String[] keys = new String[0]; // 通常情况下，没有KEYS部分
        Object[] args = new Object[]{10, 20}; // 传递给Lua脚本的参数
        Integer result = stringRedisTemplate.execute(script, Collections.emptyList(), args);
        return String.format("result is {0}", result);
    }
}