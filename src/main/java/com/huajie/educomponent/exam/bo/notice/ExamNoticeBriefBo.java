package com.huajie.educomponent.exam.bo.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Created by zgz on 2017/9/7.
 */
@Data
public class ExamNoticeBriefBo {
    private String examId;
    private String noticeName;
    private String examName;
    private String thumbnailPath;//缩略图id
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Integer duration;//考试时长min
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    private String content;//通知内容
    private String instructions;//考试须知，开始开始前的提醒
    private String orgName;
}
