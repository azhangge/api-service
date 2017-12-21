package com.huajie.educomponent.exam.bo.exam;

import com.huajie.educomponent.testpaper.bo.QuestionSetBriefBo;
import lombok.Data;
import java.util.List;

/**
 * Created by zgz on 2017/9/22.
 */
@Data
public class ExamQuestionSetBo extends QuestionSetBriefBo {

    private Float totalScore;
    private Integer strategy;//1固定 2随机
    private List<ExamQuestionSpeciesBo> species;
    private List<ExamQuestionInBo> questions;
}
