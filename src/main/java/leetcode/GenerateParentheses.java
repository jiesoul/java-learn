package leetcode;

import java.util.ArrayList;
import java.util.List;

public class GenerateParentheses {

    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        if (n == 0) {
            list.add("");
            return list;
        } else if (n == 1) {
            list.add("()");
            return list;
        } else {
            List<String> temp = generateParenthesis(n-1);
            for (String s : temp) {

            }
            return list;
        }
    }

    public static void main(String[] args) {
        GenerateParentheses parentheses = new GenerateParentheses();
        List<String> list = parentheses.generateParenthesis(3);
        for (String s : list) {
            System.out.println(s);
        }
    }
}
