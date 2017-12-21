package com.huajie.educomponent.course.constants;

/**
 * Created by 10070 on 2017/7/20.
 */
public enum CoursewareType {
    NONE(1),
    VIDEO(2),
    AUDIO(3),
    PDF(4),
    WORD(5),
    PPT(6),
    ZIP(7);

    private int value;

    CoursewareType(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}
