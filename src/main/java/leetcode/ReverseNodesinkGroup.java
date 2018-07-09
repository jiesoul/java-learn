package leetcode;

public class ReverseNodesinkGroup {

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode cur = head;
        for (int i = 0; i < k; i++) {

        }
        return head;
    }

    public ListNode reverse(ListNode head) {

        ListNode last = null;
        while (head != null) {
            ListNode temp = head;
            temp.next = null;
            head = head.next;
        }
        return last;
    }
}
