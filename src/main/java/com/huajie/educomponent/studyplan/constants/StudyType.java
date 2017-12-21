package com.huajie.educomponent.studyplan.constants;

/**
 * Created by xuxiaolong on 2017/9/19.
 */

public enum StudyType {

    QUESTIONSET(1),
    COURSE(2),
    USER(3);

    private int value;

    StudyType(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }

}
