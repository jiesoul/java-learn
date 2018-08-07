package jcip;

import java.math.BigInteger;

public class ExpensiveFuncation implements Computable<String, BigInteger> {

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        return new BigInteger(arg);
    }
}
