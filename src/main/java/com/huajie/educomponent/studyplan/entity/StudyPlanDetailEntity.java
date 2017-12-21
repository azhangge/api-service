package com.huajie.educomponent.studyplan.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by xuxiaolong on 2017/10/31.
 */
@Data
@Entity
@Table(name = "study_plan_detail")
public class StudyPlanDetailEntity extends IdEntity{
    @Column(name = "type")
    private Integer type ;
    @Column(name = "plan_id")
    private String planId;
    @Column(name = "associate_id")
    private String associateId;
    @Column(name = "is_required")
    private Boolean isRequired;
}
