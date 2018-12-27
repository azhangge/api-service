package com.huajie.service;

/**
 * @Author Administrator
 * @Date 2018/12/21 16:10
 * @Description
 */

public class ProductThread implements Runnable{

    private int p=100;

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    @Override
    public void run() {
        if (p > 100) {
            System.out.println("先停会：" + p);
        }else {
            System.out.println("生产："+ ++p);
        }
    }
}
