package com.huajie.educomponent.course.constants;

/**
 * Created by 10070 on 2017/7/20.
 */
public enum CourseApproveStatusType {
    NEW(1),
    REQUEST(2),
    APPROVING(3),
    APPROVED(4),
    REFUSED(5);

    private int value;

    CourseApproveStatusType(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}
