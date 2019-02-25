package com.jiesoul.algo;

public class Sort {

    public static int[] bubbleSort(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (a[j] < a[j-1]) {
                    int temp = a[j-1];
                    a[j-1] = a[j];
                    a[j] = temp;
                }
            }
        }

        return a;
    }

    public static int[] selectionSort(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (a[j] < a[i]) {
                    min = j;
                    exch(a, min, i);
                }
            }

        }
        return a;
    }

    public static int[] insertionSort(int[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && a[j] < a[j-1]; j--) {
                exch(a, j , j-1);
            }
        }
        return a;
    }

    public static void merge(int[] a, int low, int mid, int high) {
        int N = high - low;
        int[] b = new int[N];
        int left = low;
        int right = mid + 1;
        int bIdx = 0;
        while (left <= mid && right <= high) {
            b[bIdx++] = (a[left] <= a[right]) ? a[left++] : a[right++];
        }
        while (left <= mid) {
            b[bIdx++] = a[left++];
        }
        while (right <= high) {
            b[bIdx++] = a[right++];
        }

        for (int i = 0; i < N; i++) {
            a[low+i] = b[i];
        }
    }

    public static int[] mergeSort(int[] a, int low, int high) {
        if (low < high) {
            int mid = (low+high)/2;
            mergeSort(a,low, mid);
            mergeSort(a, mid+1, high);
            merge(a, low, mid, high);
        }
        return a;
    }

    public static int partition(int[] a, int i, int j) {
        int p = a[i];
        int m = i;
        for (int k = i+1; k <= j; k++) {
            if (a[k] < p) {
                m++;
                exch(a, k, m);
            }
        }

        exch(a, a[i], a[m]);
        return m;
    }

    public static void quickSort(int[] a, int low, int high) {
        if (low < high) {
            int pivotIdx = partition(a, low, high);
            quickSort(a, low, pivotIdx-1);
            quickSort(a, pivotIdx+1, high);
        }
    }

    private static void exch(int[] a, int i, int j) {
        int temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }

    public static void printArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        printArray(bubbleSort(new int[]{29,10,14,37,14}));
        printArray(bubbleSort(new int[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48}));
        printArray(selectionSort(new int[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48}));
        printArray(insertionSort(new int[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48}));
    }
}
