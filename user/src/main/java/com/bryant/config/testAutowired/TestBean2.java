package com.bryant.config.testAutowired;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestBean2 implements TestString {
    private static String name;

    public TestBean2(String name) {
        this.name = name;
    }

    @Override
    public String getString() {
        return name;
    }
}
