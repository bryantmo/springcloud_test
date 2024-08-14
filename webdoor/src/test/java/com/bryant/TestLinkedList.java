package com.bryant;

import java.util.LinkedList;

public class TestLinkedList {

    public static void main(String[] args) {
        LinkedList list = new LinkedList<Integer>();
        list.addLast(11);
        list.addFirst(111);
        System.out.println(list.size());
    }
}
