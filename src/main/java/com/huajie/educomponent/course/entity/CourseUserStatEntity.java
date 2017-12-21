package com.huajie.educomponent.course.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "course_user_stat")
public class CourseUserStatEntity extends IdEntity{

    @Column(name = "course_id")
    private String courseId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "total_study_time")
    private Integer totalStudyTime;//累计已学习总时长（视频）
}
