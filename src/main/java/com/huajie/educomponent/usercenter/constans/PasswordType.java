package com.huajie.educomponent.usercenter.constans;

/**
 * Created by Lenovo on 2017/8/17.
 */
public enum PasswordType {

    STRING(1),
    GESTURE(2);

    private int value;

    PasswordType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
