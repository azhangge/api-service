package com.huajie.appbase;

/**
 * Created by Lenovo on 2017/8/15.
 */
public enum BaseRetType {
    FAILED(0),
    SUCCESS(1);

    private int value;

    BaseRetType(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}
