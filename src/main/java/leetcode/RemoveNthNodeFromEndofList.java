package leetcode;

public class RemoveNthNodeFromEndofList {

    public ListNode removeNthFromEnd(ListNode head, int n) {
        int len = 1;
        ListNode node = head;
        while (node.next != null) {
            len++;
            node = node.next;
        }

        return null;

    }

}
