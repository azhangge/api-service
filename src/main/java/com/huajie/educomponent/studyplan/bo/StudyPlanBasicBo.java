package com.huajie.educomponent.studyplan.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;

/**
 * Created by xuxiaolong on 2017/10/31.
 */

@Api("学习计划基本信息")
@Data
public class StudyPlanBasicBo {
    @ApiParam("学习计划id")
    private String planId;
    @ApiParam("创建人id")
    private String creatorId;
    @ApiParam("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    @ApiParam("修改人id")
    private String modifierId;
    @ApiParam("修改人时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;
    @ApiParam("学习计划名称")
    private String planName;
    @ApiParam("学习计划开始时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @ApiParam("学习计划结束时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @ApiParam("学习计划描述")
    private String planDescription;
    @ApiParam("课程必修学时")
    private Integer requiredHours;
    @ApiParam("课程总学时")
    private Integer totalHours;
    @ApiParam("缩略图id")
    private String thumbnailId;
}
