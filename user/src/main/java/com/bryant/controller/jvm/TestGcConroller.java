package com.bryant.controller.jvm;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/jvm")
@RestController
public class TestGcConroller {

    private static final int _1MB = 1024 * 1024;

    @RequestMapping("/testGc")
    public String testGc() throws InterruptedException {
        for(int i=0;i<100;i++){
            byte[] _1M = new byte[_1MB];
            Thread.sleep(10);
        }
        return "testGc";
    }
}
