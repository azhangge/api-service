package com.huajie.educomponent.exam.entity;

import com.huajie.appbase.BaseEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by 10070 on 2017/7/24.
 */
@Data
@Entity
@Table(name = "exam_notice")
public class ExamNoticeEntity extends BaseEntity {

    @Column(name = "notice_name")
    private String noticeName;//通知名称
    @Column(name = "exam_name")
    private String examName;//考试名称
    @Column(name = "thumbnail_id")
    private String thumbnailId;//缩略图id
    @Column(name = "description")
    private String description;//说明
    @Column(name = "begin_time")
    private Date beginTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "enroll_start_time")
    private Date enrollBeginTime;
    @Column(name = "enroll_end_time")
    private Date enrollEndTime;
    @Column(name = "duration")
    private Integer duration;//考试时长min
    @Column(name="public")
    private Boolean isPublic;//是否公开考试，公开考试全平台可见考试通知
    @Column(name = "type")
    private Integer type;//1,集中考试(线上);2,普通考试（线下）
    @Column(name = "need_enroll")
    private Integer isNeedEnroll;//是否需要报名 0 不需要  1需要
    @Column(name = "position")
    private String position;//考试地点
    @Column(name = "content")
    private String content;//通知内容
    @Column(name = "instructions")
    private String instructions;//考试须知，开始开始前的提醒
    @Column(name = "total_join_num")
    private Integer totalJoinNum;//报考人数
    @Column(name = "pass_score")
    private Integer passScore;
    @Column(name = "org_name")
    private String orgName;//考试组织方
    @Column(name = "publish")
    private Boolean isPublish;//是否发布
    @Column(name = "publish_time")
    private Date publishTime;//是否发布
}