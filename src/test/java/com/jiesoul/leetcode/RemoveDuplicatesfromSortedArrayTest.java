package com.jiesoul.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoveDuplicatesfromSortedArrayTest {

    RemoveDuplicatesfromSortedArray a = new RemoveDuplicatesfromSortedArray();

    @Test
    public void removeDuplicatesTest() {
        int[] n1 = {1,1,2};
        assertEquals(2, a.removeDuplicates(n1));

        int[] n2 = {0,0,1,1,1,2,2,3,3,4};
        assertEquals(5, a.removeDuplicates(n2));
    }
}
