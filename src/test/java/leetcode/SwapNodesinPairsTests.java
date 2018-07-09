package leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwapNodesinPairsTests {

    @Test
    public void swapPairsTest() {
        SwapNodesinPairs swapNodesinPairs = new SwapNodesinPairs();
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;

        ListNode result = swapNodesinPairs.swapPairs(l1);

        assertEquals("2->1->4->3", result.toString());
    }
}
