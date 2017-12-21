package com.huajie.educomponent.portal.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created by zgz on 2017/8/21.
 * 分开部署，Bo再写一个
 */
@Data
public class PortalBriefCourseBo {

    private String courseId;
    private String courseName;
    private String thumbnailId;
    private String thumbnailPath;
    private String mainCategoryId;
    private String subCategoryId;
    private String detailCategoryId;
    private String descriptions;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String teacherId;
    private String teacherName;
    private String tags;
    private Boolean isPublic;
    private Boolean isOnShelves;
    private int approveStatus;
    private int accessCount;
    private int courseSecond;
    private Integer score = 2;
    private String creatorId;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;
    private String modifierId;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    private Date modifyDate;
}
