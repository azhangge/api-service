package com.huajie.educomponent.usercenter.constans;

/**
 * Created by 10070 on 2017/8/3.
 */
public enum AccountOpType {

    LOGIN(1),
    LOGOUT(2),
    REGISTER(3),
    FIND_PASSWORD(4),
    CHANGE_PASSWORD(5);

    private int value;

    AccountOpType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

}
