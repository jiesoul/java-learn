package leetcode;

public class RegularExpressionMatching {
    public boolean isMatch(String s, String p) {
        int sl = s.length();
        int pl = p.length();
        if (sl == 0) {
            if (pl == 0) {
                return true;
            }
            if (p.charAt(pl - 1) == '*') {
                return isMatch(s, p.substring(0, pl-2));
            }
            return false;
        } else if (sl == 1) {
            if (pl == 0) {
                return false;
            }
            int pi = pl - 1;
            char pc = p.charAt(pi);
            if (pl > 1 && pc == '*') {
                pi--;
            }



            return false;
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
