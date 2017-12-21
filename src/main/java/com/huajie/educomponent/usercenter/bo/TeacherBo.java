package com.huajie.educomponent.usercenter.bo;

import lombok.Data;

import java.util.List;

/**
 * Created by 10070 on 2017/8/12.
 */
@Data
public class TeacherBo {

    private String teacherId;
    private String userId;
    private String userRealInfoId;
    private Integer gender;
    private String realName;
    private String introPicId;   //个人风采图片ID
    private String introduction;
    private String highestTitle;//最高职称
    private String highestEdu; //最高学历
    private String identityCardId; //身份证号
    private String idCardFrontImgId;  //身份证正面照
    private String idCardBackImgId;  //身份证反面照
    private String filePath;   //头像路径
    private String idCardFrontFilePath;  //身份证正面照路径
    private String idCardBackFilePath;  //身份证反面照路径
    private Integer approveStatus;
    private List<TeacherAntecedentBo> workList;//工作经历
    private List<TeacherAntecedentBo> projectList;//项目经历
    private List<TeacherAntecedentBo> titleList;//职称
    private List<TeacherAntecedentBo> eduList;//教育经历
    private List<TeacherAntecedentBo> awardList;//奖励
}
