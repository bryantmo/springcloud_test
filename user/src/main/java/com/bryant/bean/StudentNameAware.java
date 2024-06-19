package com.bryant.bean;

import org.springframework.beans.factory.Aware;

public interface StudentNameAware extends Aware {

    /**
     * 从容器里获取到特定的name
     * @param name
     */
    void setBeanName(String name);

}
