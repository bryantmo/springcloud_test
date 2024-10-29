package com.bryant.leetcode;

import lombok.extern.slf4j.Slf4j;

/**
 * 反转链表值在[left, right]范围的链表节点
 */
@Slf4j
public class Test098Backup {

    public static void main(String[] args) {
        // testcase1
//        ListNode head = new ListNode(1);
//        ListNode node = new ListNode(2);
//        ListNode node1 = new ListNode(3);
//        ListNode node2 = new ListNode(4);
//        ListNode node3 = new ListNode(5);
//        head.next = node;
//        node.next = node1;
//        node1.next = node2;
//        node2.next = node3;

        ListNode head = new ListNode(3);
        ListNode node = new ListNode(5);
        head.next = node;

        int left = 1;
        int right = 2;

        ListNode result = reverse(head, left, right);
        while (result != null) {
            log.info("{}", result.val);
            result = result.next;
        }
    }

    private static ListNode reverse(ListNode head, int left, int right) {

        if (head == null || head.next == null) {
            return head;
        }

        // 1.找到前驱节点
        ListNode first = null;
        ListNode p = head;
        while (p != null && !(p.val >= left && p.val <= right)) {
            first = p;
            p = p.next;
        }

        // 2.反转链表（落到了[left, right]区间）
        ListNode t = new ListNode(-1);
        ListNode reverse = null;
        while (p!= null && p.val >= left && p.val <= right) {
            ListNode n = new ListNode(p.val);
            if (t.next == null) {
                t.next = n;
                reverse = n;
            } else {
                // 头插法
                n.next = t.next;
                t.next = n;
            }
            p = p.next;
        }

        // 3.拼接后续节点
        ListNode last = null;
        if (p != null) {
            last = p;
        }

        // 4.拼接
        first.next = t.next;
        if (reverse != null) {
            reverse.next = last;
        }
        return head;
    }

}