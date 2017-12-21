package com.huajie.educomponent.exam.bo.score;

import lombok.Data;

/**
 * Created by zgz on 2017/9/14.
 */
@Data
public class ExamScoreBo extends ScoreBriefBo{
    
    private String examId;
    private String examName;
    private String userId;
    private String userName;
}
