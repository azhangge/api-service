package com.huajie.educomponent.studyplan.entity;

import com.huajie.appbase.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by xuxiaolong on 2017/10/31.
 */
@Data
@Entity
@Table(name = "study_plan_basic")
public class StudyPlanBasicEntity extends BaseEntity {
    @Column(name = "plan_name")
    private String planName;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "thumbnail_id")
    private String thumbnailId;//缩略图id
    @Column(name = "plan_description", columnDefinition = "CLOB")
    private String planDescription;
    @Column(name = "required_hours")
    private Integer requiredHours;
    @Column(name = "total_hours")
    private Integer totalHours;
}
