package com.huajie.educomponent.testpaper.bo;

import lombok.Data;

/**
 * Created by zgz on 2017/9/4.
 */
@Data
public class QuestionSpeciesBo {

    private String speciesId;
    private String questionSetId;
    private Integer type;
    private String descriptions;
    private Integer speciesIndex;
    private int questionNum;
    private float speciesScore;
}
