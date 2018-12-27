package com.huajie.service;

/**
 * @Author Administrator
 * @Date 2018/12/21 16:10
 * @Description
 */

public class ConsumerThread implements Runnable{

    private int p=0;

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    @Override
    public void run() {
        if (p<5){
            System.out.println("消费停下："+ p);
        }else {
            System.out.println("消费" + --p);
        }
    }
}
