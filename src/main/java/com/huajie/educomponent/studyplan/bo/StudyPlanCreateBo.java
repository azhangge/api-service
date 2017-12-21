package com.huajie.educomponent.studyplan.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xuxiaolong on 2017/10/31.
 */
@Data
public class StudyPlanCreateBo {
    private String planId;
    private String planName;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String thumbnailId;
    private String thumbnailPath;
    private String planDescription;
    private Integer requiredHours;
    private Integer totalHours;
    private List<String> userIds;
    private List<String> questionSetIds;
    private Map<String,Boolean> courses;
}
