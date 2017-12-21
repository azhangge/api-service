package com.huajie.educomponent.testpaper.contants;

/**
 * Created by zgz on 2017/9/4.
 */
public enum TestPaperType {

    TEST(1),
    PAPER(2);

    private int value;

    TestPaperType(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }
}
