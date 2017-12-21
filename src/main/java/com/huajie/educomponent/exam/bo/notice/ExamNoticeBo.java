package com.huajie.educomponent.exam.bo.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Created by zgz on 2017/9/7.
 */
@Data
public class ExamNoticeBo {
    private String examId;
    private String noticeName;
    private String examName;
    private String thumbnailId;//缩略图id
    private String descriptions;//说明
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enrollBeginTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enrollEndTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    private Integer duration;//考试时长min
    private Boolean isPublic;// 是否公开考试，公开考试全平台可见考试通知
    private Integer type;//1,集中考试;2,普通考试
    private Integer isNeedEnroll; //是否允许报名
    private String position;//考试地点
    private String content;//通知内容
    private String instructions;//考试须知，开始开始前的提醒
    private Integer totalJoinNum;//报考人数
    private Integer passScore;
    private String orgName;
    private Boolean isPublish;
    private String thumbnailPath;//缩略图id
    private String questionSetId;
    private String paperName;
    private int userEnrollStatus; //1 已报名 0 未报名
}
