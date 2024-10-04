package com.bryant.util;

import com.alibaba.fastjson2.JSON;

public class JsonUtil {

    public static String toJson2(Object object) {
        return JSON.toJSONString(object);
    }
}
