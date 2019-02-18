package com.huajie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author Administrator
 * @Date 2018/12/21 16:07
 * @Description
 */
@RestController
@RequestMapping("/thread")
public class ThreadController {

    public static int count=5;

    public static void main(String[] args){

//            ProductThread p = new ProductThread();
//            ConsumerThread c = new ConsumerThread();
//            p.setP(count);
//            Thread pt = new Thread(p);
//            c.setP(p.getP());
//            Thread ct = new Thread(c);
//            pt.start();
//            ct.start();
//            count = c.getP();


        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("2");
        set.add("1");
        set.add("5");


    }
}
