package com.huajie.educomponent.usercenter.entity;

import com.huajie.appbase.BaseEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "teacher_brief")
public class TeacherBriefEntity extends BaseEntity{

    @Column(name = "intro_pic_id")
    private String introPicId;   //个人风采图片ID
    @Column(name = "introduction")
    private String introduction;
    @Column(name = "highest_title")
    private String highestTitle;//最高职称
    @Column(name = "highest_edu")
    private String highestEdu; //最高学历
    @Column(name = "user_id")
    private String userId;//用户id
    @Column(name = "user_real_info_id")
    private String userRealInfoId;
    @Column(name = "approve_status")
    private Integer approveStatus;//是否认证通过 0申请中 1通过 2不通过
}
