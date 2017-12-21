package com.huajie.educomponent.exam.bo.test;

import lombok.Data;

/**
 * Created by zgz on 2017/9/20.
 */
@Data
public class TestQuestionInBo {

    private String questionInId;
    private String questionSetId;//题集/试卷
    private String speciesId;
    private String questionId;
    private Integer type;
    private String statement;
    private String ContextA;
    private String ContextB;
    private String ContextC;
    private String ContextD;
    private String ContextE;
    private String ContextF;
    private String ContextG;
    private String ContextH;
    private Integer questionIndex;
    private String answer;
    private String analysis;
}
