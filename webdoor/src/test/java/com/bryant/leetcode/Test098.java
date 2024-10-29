package com.bryant.leetcode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test098 {

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

        ListNode result = reverseBetween(head, left, right);
        while (result != null) {
            log.info("{}", result.val);
            result = result.next;
        }
    }

    private static ListNode reverseBetween(ListNode head, int left, int right) {

        if (head == null || head.next == null) {
            return head;
        }

        // 虚拟节点
        ListNode tmp = new ListNode(-1);
        tmp.next = head;
        ListNode pre = tmp;

        // 1.确定头结点
        // 走left-1步，到left前一个节点
        for (int i = 0; i < left-1; i++) {
            pre = pre.next;
        }

        // 2.确定尾结点
        ListNode rightNode = pre;
        for (int i = 0; i < (right - left +1); i++) {
            rightNode = rightNode.next;
        }

        //分为3个部分：[head, pre]/[leftNode, rightNode]/[succ]
        ListNode leftNode = pre.next;
        ListNode succ = rightNode == null ? null : rightNode.next;

        // 切断链接
        pre.next = null;
        if (rightNode != null)
            rightNode.next = null;
        reverseNode(leftNode);

        // 拼接
        pre.next = rightNode;
        leftNode.next=succ;

        return tmp.next;
    }

    private static ListNode reverseNode(ListNode leftNode) {
        if (leftNode == null || leftNode.next == null) {
            return leftNode;
        }
        ListNode newNode = reverseNode(leftNode.next);
        leftNode.next.next = leftNode;
        leftNode.next = null;
        return newNode;
    }

}
 