package leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MergekSortedListsTest {

    @Test
    public void mergeKListsTest() {
        ListNode l1 = new ListNode(1);
        ListNode l12 = new ListNode(4);
        ListNode l13 = new ListNode(5);
        l1.next = l12;
        l12.next = l13;
        ListNode l2 = new ListNode(1);
        ListNode l22 = new ListNode(3);
        ListNode l23 = new ListNode(4);
        l2.next = l22;
        l22.next = l23;

        ListNode l3 = new ListNode(2);
        ListNode l32 = new ListNode(6);
        l3.next = l32;

        ListNode[] lists = {l1, l2, l3};
        MergekSortedLists mergekSortedLists = new MergekSortedLists();

        assertEquals("1->1->2->3->4->4->5->6",
                mergekSortedLists.mergeKLists(lists).toString());
    }
}
