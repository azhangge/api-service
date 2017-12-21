package com.huajie.educomponent.exam.bo.notice;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * Created by zgz on 2017/9/13.
 */
@Data
public class ExamNoticeCreateBo {
    private String examId;
    private String noticeName;
    private String examName;
    private String thumbnailId;//缩略图id
    private String description;//说明
    private Date beginTime;
    private Date endTime;
    private Date enrollBeginTime;
    private Date enrollEndTime;
    private Integer duration;//考试时长min
    private Boolean isPublic;// 是否公开考试，公开考试全平台可见考试通知
    private Integer type;//1,集中考试;2,普通考试
    private Integer isNeedEnroll; //是否允许报名
    private String position;//考试地点
    private String content;//通知内容
    private String instructions;//考试须知，开始开始前的提醒
    private Float passScore;
    private String orgName;
    private String questionSetId;//选填
    private List<String> userIds;//通知用户id
}
