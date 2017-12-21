package com.huajie.educomponent.exam.constants;


/**
 * Created by 10070 on 2017/7/24.
 */
public enum ExamEnrollType {

    ON_LINE(1),
    OFF_LINE(2);

    ExamEnrollType(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }
}
