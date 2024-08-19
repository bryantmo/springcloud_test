package com.bryant.createPattern.builder;

public class BuilderClass {
    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("chars")
                .append(11L)
                .append(11)
                .append(true)
                .append(1.1d)
                .append(1.1f)
        ;
    }
}
