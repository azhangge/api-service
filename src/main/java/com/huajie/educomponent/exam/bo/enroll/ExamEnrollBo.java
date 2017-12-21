package com.huajie.educomponent.exam.bo.enroll;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Created by zgz on 2017/9/14.
 */
@Data
public class ExamEnrollBo {

    private String userId;
    private String userName;
    private String examId;
    private String examName;
    private Integer type; //1 在线; 2 线下
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Integer duration;//考试时长min
    private String orgName;
}
