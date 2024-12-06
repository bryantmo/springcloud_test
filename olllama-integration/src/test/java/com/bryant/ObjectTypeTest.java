package com.bryant;

import com.bryant.service.util.ObjectTypeEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectTypeTest {
    public static void main(String[] args)
    {
        StringBuilder sb = new StringBuilder();
        for (ObjectTypeEnum objectTypeEnum : ObjectTypeEnum.values()) {
            log.info(objectTypeEnum.getValue() + "-" + objectTypeEnum.getChName());
            sb.append(objectTypeEnum.getValue() + "-" + objectTypeEnum.getChName() + ",");
        }
        log.info(sb.toString());
    }
}
