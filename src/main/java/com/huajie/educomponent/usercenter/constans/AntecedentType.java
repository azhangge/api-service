package com.huajie.educomponent.usercenter.constans;

/**
 * Created by Lenovo on 2017/8/17.
 */
public enum AntecedentType {

    EDU(1),
    WORK(2),
    PROJECT(3),
    TITLE(4),
    AWARD(5);

    private int value;

    AntecedentType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
