package com.huajie.educomponent.exam.bo.exam;

import lombok.Data;

/**
 * Created by zgz on 2017/9/22.
 */
@Data
public class ExamQuestionSpeciesBo {
    private String speciesId;
    private String questionSetId;
    private Integer type;
    private String descriptions;
    private Integer speciesIndex;
    private int questionNum;
    private Float speciesScore;
}
