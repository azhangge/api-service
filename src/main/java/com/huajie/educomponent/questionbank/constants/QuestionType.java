package com.huajie.educomponent.questionbank.constants;

/**
 * Created by 10070 on 2017/7/27.
 */
public enum QuestionType {

    SINGLE_CHOICE(1),
    MULTI_CHOICE(2),
    JUDGE(3),
    BLANK_FILLING(4),
    Q_A(5);

    private int value;

    QuestionType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

}
