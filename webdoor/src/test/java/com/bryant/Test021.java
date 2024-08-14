package com.bryant;

/**
 * https://leetcode.cn/problems/merge-two-sorted-lists/description/
 *
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 */
public class Test021 {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(4);
        ListNode l4 = new ListNode(5);

    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1 == null && l2 == null){
            return null;
        }

        ListNode cur = l1.next;
        ListNode l2_p = l2;
        while (cur != null) {

            // 中间变量：l1 下次指向元素
            ListNode next2 = cur.next;

            // 插入l2
            while (l2_p.next != null
                    && cur.val >= l2_p.next.val) {
                l2_p = l2_p.next;
            }

            // 逃脱出来，说明找到了插入点
            ListNode newNext = l2_p.next;
            l2_p.next = cur;
            cur.next = newNext;

            cur = next2;
        }
        return l2;
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
