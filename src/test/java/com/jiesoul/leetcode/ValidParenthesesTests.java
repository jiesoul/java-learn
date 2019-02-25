package com.jiesoul.leetcode;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidParenthesesTests {

    @Test
    public void isValidTest () {
        assertEquals(true, new ValidParentheses().isValid("()"));
        assertEquals(true, new ValidParentheses().isValid("()[]{}"));
        assertEquals(false, new ValidParentheses().isValid("(]"));
        assertEquals(false, new ValidParentheses().isValid("(])]"));
        assertEquals(true, new ValidParentheses().isValid("{[]}"));
        assertEquals(false, new ValidParentheses().isValid("}"));
        assertEquals(false, new ValidParentheses().isValid("]"));
        assertEquals(false, new ValidParentheses().isValid(")"));
        assertEquals(false, new ValidParentheses().isValid("("));
        assertEquals(false, new ValidParentheses().isValid("["));
        assertEquals(false, new ValidParentheses().isValid("{"));
    }

}
