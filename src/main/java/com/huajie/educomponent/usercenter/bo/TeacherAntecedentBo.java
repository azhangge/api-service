package com.huajie.educomponent.usercenter.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created by 10070 on 2017/8/12.
 */
@Data
public class TeacherAntecedentBo {

    private String antecedentId;
    private String teacherId;
    private Integer type; //1 教育;2 工作;3 项目;4 职称;5 荣誉
    private String orgName; //学校名称/公司名称/项目名称
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM")
    private Date endTime;
    private String title;
    private String titleImgId;
    private String titleImgPath;
    private String descriptions; //备注/说明
}
