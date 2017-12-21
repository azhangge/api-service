package com.huajie.educomponent.exam.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by zgz on 2017/9/7.
 */
@Data
@Entity
@Table(name = "exam_notice_user")
public class ExamNoticeUserEntity extends IdEntity {

    @Column(name = "exam_id")
    private String examId;//用户已读通知保存记录
    @Column(name = "user_id")
    private String userId;
    @Column(name = "read_status")
    private Integer readStatus; //1 已读  0未读
}
