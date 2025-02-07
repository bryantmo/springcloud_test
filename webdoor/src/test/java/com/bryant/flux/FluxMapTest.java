package com.bryant.flux;

import reactor.core.publisher.Flux;

public class FluxMapTest {

    public static void main(String[] args) {
//        Flux.range(1, 10).map(x -> x*x).subscribe(System.out::println);
        Flux.range(1, 10).map(x -> {
            return new Num(x);
        }).toStream().forEach(System.out::println);
    }
}

class Num {
    private int num;

    public Num(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}