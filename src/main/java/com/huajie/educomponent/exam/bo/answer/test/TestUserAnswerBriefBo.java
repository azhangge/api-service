package com.huajie.educomponent.exam.bo.answer.test;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Created by zgz on 2017/9/20.
 */
@Data
public class TestUserAnswerBriefBo {

    private String questionSetId;
    private String questionSetInsId;
    private String name;
    private int questionNum;
    private int rightNum;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
    private int type;//questionSetType  1题集  2试卷
}
