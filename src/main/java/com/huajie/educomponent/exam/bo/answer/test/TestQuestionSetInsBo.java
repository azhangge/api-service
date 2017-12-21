package com.huajie.educomponent.exam.bo.answer.test;

import com.huajie.educomponent.exam.bo.test.TestQuestionSpeciesBo;
import lombok.Data;
import java.util.List;

/**
 * Created by zgz on 2017/9/20.
 */
@Data
public class TestQuestionSetInsBo extends TestUserAnswerBriefBo {

    private List<TestQuestionSpeciesBo> species;
    private List<TestQuestionInsBo> questions;
}
