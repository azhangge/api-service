package com.huajie.educomponent.exam.bo.test;

import lombok.Data;

/**
 * Created by zgz on 2017/9/25.
 */
@Data
public class TestQuestionSpeciesBo {
    private String speciesId;
    private String questionSetId;
    private Integer type;
    private String descriptions;
    private Integer speciesIndex;
    private int questionNum;
}
