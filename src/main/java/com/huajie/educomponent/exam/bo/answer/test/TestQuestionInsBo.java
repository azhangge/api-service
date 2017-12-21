package com.huajie.educomponent.exam.bo.answer.test;

import com.huajie.educomponent.exam.bo.test.TestQuestionInBo;
import lombok.Data;

/**
 * Created by zgz on 2017/9/20.
 */
@Data
public class TestQuestionInsBo extends TestQuestionInBo {

    private String  userAnswer;
    private Boolean isRight;
}
