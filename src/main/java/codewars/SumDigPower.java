package codewars;

import java.util.ArrayList;
import java.util.List;

public class SumDigPower {
    public static List<Long> sumDigPow(long a, long b) {
        List<Long> result = new ArrayList<>();
        for (long i = a; i <= b; i++) {
            List<Long> ll = spiltNum(i);
            if (i == sumList(ll)) {
                result.add(i);
            }
        }
        return result;
    }

    public static List<Long> spiltNum(long n) {
        List<Long> ll = new ArrayList<>();
        do {
            ll.add(n%10);
        } while (n/10 != 0);
        return ll;
    }

    public static long sumList(List<Long> longs) {
        long result = -1;
        int len = longs.size();
        if (len > 0) {
            Long[] ss = (Long[]) longs.toArray();
            for (int i = 0; i < len; i++) {
                result += Math.pow(ss[i], len-i);
            }
        }
        return result;

    }
}
