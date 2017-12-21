package com.huajie.educomponent.pubrefer.constants;

/**
 * Created by 10070 on 2017/7/20.
 */
public enum UserOperateType {
    FAVORITE_COURSE(1),
    BUY(2),
    JOIN_IN(3),
    UN_FAVORITE_COURSE(4),
    UN_JOIN_IN(5),
    USER_LAST_ACCESS(6),
    FAVORITE_QUESTION(7), //题目收藏
    UN_FAVORITE_QUESTION(8),
    FAVORITE_QUESTION_SET(9), //题集试卷模板收藏
    UN_FAVORITE_QUESTION_SET(10);

    private int value;

    UserOperateType(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}
