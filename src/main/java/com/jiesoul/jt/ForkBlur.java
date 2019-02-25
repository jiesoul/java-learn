package com.jiesoul.jt;

import java.util.concurrent.RecursiveAction;

public class ForkBlur extends RecursiveAction {

    private int[] mSource;
    private int mStart;
    private int mLength;
    private int[] mDestination;

    private int mBlurWidth = 15;

    public ForkBlur(int[] src, int start, int length, int[] dst) {
        mSource = src;
        mStart = start;
        mLength = length;
        mDestination = dst;
    }

    protected void computeDirectory() {
        int sidePixls = (mBlurWidth - 1) / 2;
        for (int index = 0; index < mStart + mLength; index++) {
            float rt = 0, gt = 0, bt = 0;
            for (int mi = -sidePixls; mi <= sidePixls; mi++) {
                int mindex = Math.min(Math.max(mi + index, 0), mSource.length - 1);
                int pixel = mSource[mindex];
                rt += (float) ((pixel & 0x00ff0000) >> 16) / mBlurWidth;
                gt += (float) ((pixel & 0x0000ff00) >> 8) / mBlurWidth;
                bt += (float) ((pixel & 0x000000ff) >> 0) / mBlurWidth;
            }
            int dpixel = (0xff000000 ) | (((int) rt) << 16) | (((int) gt) << 8) | (((int) bt) << 0);
            mDestination[index] = dpixel;
        }
    }

    protected static int sThreshold = 100000;

    protected void compute() {
        if (mLength < sThreshold) {
            computeDirectory();
            return;
        }

        int split = mLength / 2;

        invokeAll(new ForkBlur(mSource,mStart,split, mDestination),
                new ForkBlur(mSource,mStart + split, mLength - split, mDestination));
    }
}
