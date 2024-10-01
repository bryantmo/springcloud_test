package com.bryant;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Test002 {
    public static void main(String[] args) {
        //[2,4,3]
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);
        //[5,6,4]
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode r = addTwoNumbers(l1, l2);
        while(r != null) {
            log.info("r = {}", r.val);
            r = r.next;
        }
    }
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode h = null;
        ListNode tail = null;
        int carry = 0;

        // 迭代遍历链表
        while(l1 != null || l2 != null) {

            int n1 = (l1 != null) ? l1.val : 0;
            int n2 = (l2 != null) ? l2.val : 0;
            int tmp = n1 + n2 + carry;

            ListNode node = new ListNode(tmp % 10);
            if (h == null || tail == null) {
                h = node;
                tail = node;
            } else {
                // 尾插法
                tail.next = node;
                tail = node;
            }

            carry = tmp /10;

            if (l1 != null) {
                l1 = l1.next;
            } else {
                l1 = null;
            }

            if (l2 != null) {
                l2 = l2.next;
            } else {
                l2 = null;
            }
        }

        if (carry == 1) {
            ListNode carryNode = new ListNode(1);
            // 尾插法
            tail.next = carryNode;
            tail = carryNode;
        }

        return h;
    }
}
