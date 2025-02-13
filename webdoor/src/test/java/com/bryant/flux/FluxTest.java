package com.bryant.flux;

import reactor.core.publisher.Flux;

public class FluxTest {
    public static void main(String[] args) {
        String src = "Hello world, i am bryant mo...";
        Flux.fromArray(src.split(" "))// 处理前的长字符串集合，用一个流对集合对象操作
                .flatMap(i -> {
                    // i是src.split(" ")处理后的一个元素：短字符串集合，用一个流对集合对象的每个元素操作
                    System.out.println("1- "+i);
                    return Flux.fromArray(i.split(""));
                }) //flatMap一个输入一个输出
                .distinct()// 去重
                .sort()
                .subscribe( j -> {
                    // j是i.split("")处理后的一个元素：单字符
                    System.out.println("2- "+j);
                });
    }
}
