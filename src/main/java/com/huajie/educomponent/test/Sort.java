package com.huajie.educomponent.test;

import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sort {

    public static void main(String[] args){
        Integer[] str1 = {2,1,5,8};
        int[] str2 = {9,2,22};


//        List<Integer> list1 = new ArrayList<>();
//        List<Integer> list2 = new ArrayList<>();
//        list1.add(10);
//        list1.add(5);
//        list1.add(2);
//        list1.add(8);
//
//        list2.add(22);
//        list2.add(12);
//        list2.add(3);
//        list2.add(5);
//
//        list1.addAll(list2);
//
//        Collections.sort(list1);
//
//        System.out.println(list1.toString());

        ArrayList<Integer> str4  = new ArrayList<>(Arrays.asList(str1)) ;

        System.out.println(str4.toString());

    }
}
