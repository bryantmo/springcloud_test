package com.bryant.controller;

import com.bryant.config.testAutowired.TestBean;
import com.bryant.config.testAutowired.TestBean2;
import com.bryant.config.testAutowired.TestString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/bean")
@Slf4j
public class BeanController {

    @Autowired
    private TestString testBean;

    @Resource
    private List<TestString> testBeans;

    @Autowired
    @Qualifier(value = "testBean3")
    private TestBean testBean3;

//    @Autowired
//    @Qualifier(value = "testBean4")
//    private TestBean2 testBean2;
//
//    @Resource(name = "testBean4")
//    private TestBean2 testBean2;

    @Resource(type = TestBean2.class, name = "testBean4")
    private TestBean2 testBean2;

    @GetMapping("/testBean")
    public String test() {
        return testBean.getString();
    }

    @GetMapping("/testBean2")
    public String test2() {
        StringBuilder stringBuilder = new StringBuilder();
        for (TestString testString : testBeans) {
            log.info("testString: {}", testString.getString());
            stringBuilder.append(testString.getString());
        }
        return stringBuilder.toString();
    }

    @GetMapping("/testBean3")
    public String testBean3() {
        return testBean3.getString();
    }

    @GetMapping("/testBean4")
    public String testBean4() {
        return testBean2.getString();
    }
}
