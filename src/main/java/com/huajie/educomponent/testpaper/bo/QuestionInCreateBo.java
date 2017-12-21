package com.huajie.educomponent.testpaper.bo;

import lombok.Data;

/**
 * Created by zgz on 2017/9/18.
 */
@Data
public class QuestionInCreateBo {
    private String questionInId;
    private String questionSetId;//题集/试卷
    private String speciesId;
    private String questionId;
    private Integer questionIndex;
    private float score;
}
