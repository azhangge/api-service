package com.huajie.educomponent.course.constants;

/**
 * Created by wangd on 2017/9/19.
 */

public enum IndexType {

    HOTCOURSE("最热课程"),
    NEWCOURSE("最新课程");

    private String value;

    IndexType(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }

}
