package com.huajie.controller.algorithm;

import java.util.Arrays;

/**
 * @Author Administrator
 * @Date 2018/12/28 10:58
 * @Description
 * 找到最小的，和第一个换
 * 找到最小的，和第二个换
 */

public class 选择排序 {
    public static void main(String[] args){
        int[] init = new int[]{3,2,1,9,3,11,88,3,7};
        int temp= 0;
        int count = 0;
        for (int i = 0;i<init.length;i++){
            int minIndex = i;
            for (int j = i+1; j<init.length;j++){
                if (init[minIndex] > init[j]){
                    minIndex = j;
                }
            }
            temp = init[i];
            init[i] = init[minIndex];
            init[minIndex] = temp;
            count++;

        }
        System.out.println(Arrays.toString(init));
        System.out.println("次数"+count);
    }
}
