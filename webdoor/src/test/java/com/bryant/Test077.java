package com.bryant;

/**
 * 归并算法
 * 二分法找中点
 * 递归二分（前子序列 + 后子序列）
 * 合并（两个序列），用到新的链表
 */
public class Test077 {

    public static void main(String[] args) {
        new ListNode(-1);
        ListNode listNode1 = new ListNode(4);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(1);
        ListNode listNode4 = new ListNode(3);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        sortList(listNode1);
    }

    public static ListNode sortList(ListNode head) {
        // 找mid点
        return nodeSort(head, null);
    }
    private static ListNode nodeSort(ListNode head, ListNode tail) {

        if (head == null) {
            return head;
        }

        if (head.next == tail) {
            head.next = null;
            return head;
        }

        ListNode fast = head, slow = head;

        while (fast != tail) {
            // slow 移动1步
            slow = slow.next;
            fast = fast.next;
            // fast 移动2步
            if (fast != tail) {
                fast = fast.next;
            }
        }

        ListNode mid = slow;
        ListNode list1 = nodeSort(head, mid);
        ListNode list2 = nodeSort(mid, tail);
        return nodeMerge(list1, list2);
    }
    private static ListNode nodeMerge(ListNode head, ListNode head2) {

        ListNode tmp = new ListNode(-1);
        ListNode temp1 = head, temp2 = head2, mov = tmp;


        // 合并子序列
        while (temp1 != null && temp2 != null) {
            // 升序
            if (temp1.val > temp2.val) {
                mov.next = temp2;
                temp2 = temp2.next;
            } else {
                mov.next = temp1;
                temp1 = temp1.next;
            }
            mov = mov.next;
        }

        // t1子序列已经合并完成，剩下t2
        if (temp1 == null) {
            mov.next = temp2;
        } else if (temp2 == null) {
            // t2子序列已经合并完成，剩下t1
            mov.next = temp1;
        }

        return tmp.next;
    }
}
