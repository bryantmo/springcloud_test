package com.bryant.syntax;

public class InnerClassTest {
    InnerClass innerClass = new InnerClass();
    public InnerClassTest(){
        System.out.println("InnerClassTest");
    }
    public static void main(String[] args) {
        new InnerClassTest();
    }
}

class InnerClass{
    public InnerClass() {
        System.out.println("InnerClass");
    }
}
