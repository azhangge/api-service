package com.huajie.controller.algorithm;

import java.util.Arrays;

/**
 * @Author Administrator
 * @Date 2019/2/18 10:09
 * @Description
 * 一趟快速排序的算法是：
 *
 * 1）设置两个变量i、j，排序开始的时候：i=0，j=N-1；
 *
 * 2）以第一个数组元素作为关键数据，赋值给key，即key=A[0]；
 *
 * 3）从j开始向前搜索，即由后开始向前搜索(j--)，找到第一个小于key的值A[j]，将A[j]和A[i]互换；
 *
 * 4）从i开始向后搜索，即由前开始向后搜索(i++)，找到第一个大于key的A[i]，将A[i]和A[j]互换；
 *
 * 5）重复第3、4步，直到i=j； (3,4步中，没找到符合条件的值，即3中A[j]不小于key,4中A[i]不大于key的时候改变j、i的值，使得j=j-1，i=i+1，直至找到为止。找到符合条件的值，进行交换的时候i， j指针位置不变。另外，i==j这一过程一定正好是i+或j-完成的时候，此时令循环结束）
 *
 * 直到分组不能再分，即数组只有一个数
 */

public class 快速排序 {
    public static void main(String[] args) {
        int[] init = {3, 2, 1, 9, 3, 11, 88, 3, 7};
        System.out.println(Arrays.toString(init));
        sort(init,0,init.length-1);
        System.out.println(Arrays.toString(init));
    }

    public static void sort(int[] a, int low, int height) {
        int i = low;
        int j = height;
        if (i > j) { //放在k之前，防止下标越界
            return;
        }
        int k = a[i];
        while (i < j) {
            while (i < j && a[j] > k) {//找出小的数
                j--;
            }
            while (i < j && a[i] <= k) {//找出大的数
                i++;
            }
            if (i < j) {//交换
                int swap = a[i];
                a[i] = a[j];
                a[j] = swap;
            }
        }
        //交换K
        k = a[i];
        a[i] = a[low];
        a[low] = k;

        //对左边进行排序,递归算法
        sort(a, 0, i - 1);
        //对右边进行排序
        sort(a, i + 1, height);
    }
}
