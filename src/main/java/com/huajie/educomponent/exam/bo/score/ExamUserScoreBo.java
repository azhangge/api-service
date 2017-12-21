package com.huajie.educomponent.exam.bo.score;

import lombok.Data;
import java.util.List;

/**
 * Created by zgz on 2017/9/26.
 */
@Data
public class ExamUserScoreBo {
    private String examId;
    private String examName;
    private List<ScoreBriefBo> examScores;
}
