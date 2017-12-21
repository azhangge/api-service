package com.huajie.educomponent.usercenter.bo;

import lombok.Data;

/**
 * Created by zgz on 2017/9/9.
 */
@Data
public class TeacherCreateBo {
    private String teacherId;
    private String userId;
    private String introPicId;   //个人风采图片ID
    private String introduction;
    private String highestTitle;//最高职称
    private String highestEdu; //最高学历
    private Integer gender;
    private String realName;
}
