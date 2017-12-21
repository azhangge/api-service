package com.huajie.educomponent.questionbank.constants;

/**
 * Created by zgz on 2017/9/4.
 */
public enum UserBanksType {

    USER_CREATE_QUESTION(1),
    FAVORITE_QUESTION(2),
    OPEN_QUESTION(3),//公开的
    ERROR_QUESTION(4),;

    private int value;

    UserBanksType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
