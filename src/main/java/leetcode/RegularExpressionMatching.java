package leetcode;

public class RegularExpressionMatching {
    public boolean isMatch(String s, String p) {
        int sl = s.length();
        int pl = p.length();
        if (sl == 0) {
            if (pl == 0) {
                return true;
            } else {
                if (pl > 1 && p.charAt(1) == '*') {
                    return isMatch(s, p.substring(2));
                } else {
                    return false;
                }

            }
        } else {
            if (pl == 0) {
                return false;
            } else {
                char sc = s.charAt(0);
                char pc = p.charAt(0);
                if (sc == pc || pc == '.') {
                    if (pl > 1 && p.charAt(1) == '*') {
                        return isMatch(s.substring(1), p);
                    } else {
                        return isMatch(s.substring(1), p.substring(1));
                    }
                } else {
                    if (pl > 1 && p.charAt(1) == '*') {
                        return isMatch(s, p.substring(2));
                    } else {
                        return false;
                    }
                }
            }
        }


    }

    public static void main(String[] args) {
        RegularExpressionMatching matching = new RegularExpressionMatching();
        System.out.println(matching.isMatch("aaa", "a*a"));
        System.out.println(matching.isMatch("aab", "c*a*b"));
        System.out.println(matching.isMatch("ab", ".*c"));
        System.out.println(matching.isMatch("aaa", "aaaa"));
        System.out.println(matching.isMatch("mississippi", "mis*is*p*."));
        System.out.println(matching.isMatch("aa", "a*"));
        System.out.println(matching.isMatch("aa", "a"));
        System.out.println(matching.isMatch("ab", ".*"));
    }
}
