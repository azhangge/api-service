package com.huajie.educomponent.exam.bo.exam;

import lombok.Data;

/**
 * Created by zgz on 2017/9/22.
 */
@Data
public class ExamQuestionInBo {

    private String questionInId;
    private String questionSetId;//题集/试卷(随机试卷，这里是实例id)
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
    private Float score;
}
