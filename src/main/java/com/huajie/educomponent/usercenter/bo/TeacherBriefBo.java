package com.huajie.educomponent.usercenter.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;


/**
 * Created by 10070 on 2017/8/12.
 */
@Data
public class TeacherBriefBo {

    private String teacherId;
    private String userId;
    private String introPicId;   //个人风采图片ID
    private String introduction;
    private String creatorId;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String highestTitle;//最高职称
    private String highestEdu; //最高学历
    private Integer gender;
    private String realName;//实名
    private String introPicPath;//个人风采图片
    private Integer approveStatus;
}
