package com.huajie.educomponent.questionbank.bo;

import lombok.Data;

/**
 * Created by zgz on 2017/7/21.
 */
@Data
public class QuestionBo {

    private String questionId;
    private Integer type;
    private String mainCategoryId;
    private String subCategoryId;
    private Integer isPublic;
    private String tags;
    private String parentId;
    private String subIndex;
    private String statement;
    private String answer;
    private String analysis;
    private String contextA;
    private String contextB;
    private String contextC;
    private String contextD;
    private String contextE;
    private String contextF;
    private String contextG;
    private String contextH;

}
