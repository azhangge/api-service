package com.huajie.educomponent.suggestion.constants;

/**
 * Created by zgz on 2017/9/6.
 */
public enum SuggestionType {
    COURSE(1),
    EXAM(2),
    TEST(3),
    OTHER(4);

    private int value;

    SuggestionType(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}
