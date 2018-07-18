package leetcode;

public class DivideTwoIntegers {

    public int divide(int dividend, int divisor) {
        int r = 0;
        int d = Math.abs(dividend);
        int v = Math.abs(divisor);
        while (d >= v && r < Integer.MAX_VALUE) {
            r++;
            d = d - v;
        }

        if ((dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0)) {
            return r;
        } else {
            return -r;
        }
    }

    public static void main(String[] args) {
        DivideTwoIntegers d = new DivideTwoIntegers();
        System.out.println(d.divide(-10, 3));
        System.out.println(d.divide(-2147483648, -1));
        System.out.println(d.divide(7, -3));
    }
}
