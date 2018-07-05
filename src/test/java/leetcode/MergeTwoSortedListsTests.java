package leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MergeTwoSortedListsTests {

    @Test
    public void mergeTwoListsTest() {
        ListNode l11 = new ListNode(1);
        ListNode l12 = new ListNode(2);
        ListNode l13 = new ListNode(4);
        l11.next = l12;
        l12.next = l13;

        ListNode l21 = new ListNode(1);
        ListNode l22 = new ListNode(3);
        ListNode l23 = new ListNode(4);
        l21.next = l22;
        l22.next = l23;

        assertEquals("1->1->2->3->4->4", new MergeTwoSortedLists().mergeTwoLists(l11, l21).toString());
    }
}
