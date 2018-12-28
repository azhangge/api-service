package com.huajie.controller.algorithm;

import java.util.Arrays;

/**
 * @Author Administrator
 * @Date 2018/12/28 10:07
 * @Description
 */

//前面22比较的比较，往前插入排序，保证前面的是完好序列
public class 插入排序 {
    public static void main(String[] args){
        int[] init = new int[]{3,2,1,9,3,11,88,3,7};
        int temp= 0;
        int count = 0;
        for (int i=1;i<init.length;i++){
            for (int j=i;j>0;j--){
                if (init[j] < init[j-1]){
                    temp = init[j];
                    init[j] = init[j-1];
                    init[j-1] = temp;
                }
                count ++;
            }
        }
        System.out.println(Arrays.toString(init));
        System.out.println("次数"+count);
    }
}
