package com.bryant;

/**
 * https://leetcode.cn/problems/merge-two-sorted-lists/description/
 *
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 */
public class Test021 {

    public static void main(String[] args) {

    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // firstHead 是一个头结点
        ListNode firstHead = new ListNode(-1);
        // first 是一个动态指针
        ListNode first = firstHead;
        // 两个链表都不为空时，则顺序比较每个item值
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                first.next = l1;
                l1 = l1.next;
            } else {
                first.next = l2;
                l2 = l2.next;
            }
            // 移动first指针
            first = first.next;
        }
        first.next = (l1 == null) ? l2: l1;
        return firstHead.next;
    }


}

class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
