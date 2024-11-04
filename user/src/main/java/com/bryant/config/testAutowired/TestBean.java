package com.bryant.config.testAutowired;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TestBean implements TestString {
    private static String name;

    public TestBean(String name) {
        this.name = name;
    }

    @Override
    public String getString() {
        return name;
    }
}
