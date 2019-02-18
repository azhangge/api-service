package com.huajie.controller.algorithm;

import java.util.Arrays;

/**
 * @Author Administrator
 * @Date 2019/2/18 9:52
 * @Description
 * 冒泡排序是插入排序的一个变种，复杂度一样
 * 22循环比较，找到最小或最大的放在一边作为排好序的
 */

public class 冒泡排序 {
    public static void main(String[] args){
        int[] init = new int[]{3,2,1,9,3,11,88,3,7};
        int temp= 0;
        int count = 0;
        for (int i = 0;i <init.length;i++){
            for (int j = i+1;j<init.length;j++){
               if (init[i] > init[j]){
                   temp = init[j];
                   init[j] = init[i];
                   init[i] = temp;
               }
               count++;
            }
        }
        System.out.println(Arrays.toString(init));
        System.out.println("次数"+count);
    }
}
