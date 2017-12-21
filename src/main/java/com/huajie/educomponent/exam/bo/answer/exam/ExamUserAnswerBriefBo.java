package com.huajie.educomponent.exam.bo.answer.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Created by zgz on 2017/9/23.
 */
@Data
public class ExamUserAnswerBriefBo {

    private String questionSetId;
    private String questionSetInsId;
    private String examId;
    private String name;
    private int questionNum;
    private int rightNum;
    private float userScore;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
    private int type;//questionSetType  1题集  2试卷
}
