package com.huajie.educomponent.exam.constants;

/**
 * Created by zgz on 2017/9/14.
 */
public enum ExamEnrollOperateType {

    ENROLL(1),
    UN_ENROLL(2);

    ExamEnrollOperateType(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }
}
