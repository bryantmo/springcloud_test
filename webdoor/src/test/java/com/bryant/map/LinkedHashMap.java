package com.bryant.map;

public class LinkedHashMap {
    public static void main(String[] args) {
        java.util.LinkedHashMap<Integer, String> map = new java.util.LinkedHashMap<>();
        map.put(3, "3");
        map.put(1, "1");
        map.put(2, "2");

        System.out.println(map.get(0));
        System.out.println(map.keySet());
        System.out.println(map.keySet().iterator().next());
    }
}
