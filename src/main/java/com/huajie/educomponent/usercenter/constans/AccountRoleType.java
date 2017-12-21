package com.huajie.educomponent.usercenter.constans;

/**
 * Created by zgz on 2017/9/29.
 */
public enum AccountRoleType {

    NORMAL(0),//普通
    TEACHER(1),//讲师
    MANAGER(2);//管理员

    private int value;

    AccountRoleType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
