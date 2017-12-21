package com.huajie.educomponent.exam.bo.score;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Created by zgz on 2017/9/26.
 */
@Data
public class ScoreBriefBo {

    private float userScore;//用户成绩
    private int userDuration;//用户考试用时
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
}
