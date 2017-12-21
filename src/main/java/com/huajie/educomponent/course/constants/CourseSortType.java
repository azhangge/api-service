package com.huajie.educomponent.course.constants;

/**
 * Created by Lenovo on 2017/8/21.
 */
public enum CourseSortType {
    NEWEST(1),
    HOTEST(2);

    private int value;

    CourseSortType(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}
