package com.bryant.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentBean implements StudentNameAware, InitializingBean {
    private Integer id;
    private String name;

    @Override
    public void setBeanName(String name) {
        this.name = name;
        log.info(String.format("StudentBean setBeanName, name = %s", name));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(String.format("InitializingBean afterPropertiesSet, name = %s", name));
        Asserts.notBlank("name is null..", this.name);
    }
}
