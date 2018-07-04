package leetcode;

import java.util.Arrays;

public class ThreeSumClosest {

    public int threeSumClosest(int[] nums, int target) {
        int len = nums.length;
        if (len < 3) {
            throw  new IllegalArgumentException("error args");
        }
        Arrays.sort(nums);

        int result = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < nums.length-2; i++) {
            int twoResult = twoSumClosest(nums, target - nums[i], i+1);
            if (Math.abs(target - twoResult - nums[i]) < Math.abs(target - result)) {
                result = twoResult + nums[i];
            }
        }
        return result;
    }

    private int twoSumClosest(int[] nums, int target, int index) {
        int l = index, r = nums.length - 1;
        int result = 0;
        while (l < r) {
            int temp = target - (nums[l] + nums[r]);
            if (Math.abs(temp) < Math.abs(target - result)) {
                result = temp;
            }
            if (temp == 0) {
                break;
            } else if (temp > 0) {
                l++;
            } else {
                r--;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        ThreeSumClosest tsc = new ThreeSumClosest();
        int[] a = new int[]{-1, 2, 1, -4};
        int target = 1;
        System.out.println(tsc.threeSumClosest(a, target));
    }

}
