package com.kinglin.pet;

import java.util.concurrent.CompletableFuture;

public class LeeCode {
    public static void main(String[] args) {
        CompletableFuture f1 = CompletableFuture.runAsync(() -> {
            long startTime = System.currentTimeMillis();
            System.out.println(add(999));
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime + "ms");
        });
        CompletableFuture f2 = CompletableFuture.runAsync(() -> {
            long startTime = System.currentTimeMillis();
            System.out.println(tailAdd(999, 0));
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime + "ms");
        });
        CompletableFuture.allOf(f1, f2).join();
    }

    public static int add(int n) {
        if (n == 1) {
            return n;
        }
        return add(n - 1) + n;
    }

    public static int tailAdd(int n, int result) {
        if (n == 0) {
            return result;
        }
        return tailAdd(n - 1, n + result);
    }

    /**
     * 无重复字符的最长子串
     */
    public static int lengthOfLongestSubstring(String s) {
        // 记录字符上一次出现的位置
        int[] last = new int[128];
        for (int i = 0; i < 128; i++) {
            last[i] = -1;
        }
        int n = s.length();
        int res = 0;
        int start = 0; // 窗口开始位置
        for (int i = 0; i < n; i++) {
            int index = s.charAt(i);
            start = Math.max(start, last[index] + 1);
            res = Math.max(res, i - start + 1);
            last[index] = i;
        }
        return res;
    }

}
