package leetcode;

public class RegularExpressionMatching {
    public boolean isMatch(String s, String p) {

        if (!p.contains(".") && !p.contains("*")) {
            return s.equals(p);
        } else if (p.contains(".*")) {
            return true;
        } else {
            boolean match = false;

            for (int i=0; i < s.length(); i++) {
                if (p.length() <= 0) {
                    return false;
                }
                char c = s.charAt(i);
                int idx = p.indexOf(c);
                if (idx == -1) {
                    idx = p.indexOf(".");
                    if (idx == -1) {
                        return false;
                    }
                }
                for (int j=0; j < p.length(); j++) {
                    char a = p.charAt(j);

                }
            }
            return match;
        }

    }
}
