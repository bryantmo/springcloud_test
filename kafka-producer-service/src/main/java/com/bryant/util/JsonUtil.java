package com.bryant.util;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson2(Object object) {
        return JSON.toJSONString(object);
    }
}
