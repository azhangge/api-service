package com.huajie.educomponent.usercenter.entity;

import com.huajie.appbase.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "teacher_antecedents")
public class TeacherAntecedentsEntity extends BaseEntity {

    @Column(name = "teacher_id")
    private String teacherId;
    @Column(name = "type")
    private int type; //1 教育;2 工作;3 项目名称; 4 职称; 5 荣誉
    @Column(name = "org_name")
    private String orgName; //学校名称/公司名称/项目名称
    @Column(name = "start_time")    //起始时间/职称获得时间
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "title")   //学历名称/公司职务/项目角色/职称名称/获奖荣誉
    private String title;
    @Column(name = "title_img_id")
    private String titleImgId;
    @Column(name = "descriptions")
    private String descriptions; //备注/说明
}
