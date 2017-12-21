package com.huajie.educomponent.testpaper.bo;

import lombok.Data;

/**
 * Created by zgz on 2017/9/4.
 */
@Data
public class QuestionInBo {

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
    private float score;
    private String answer;
    private String analysis;
}
