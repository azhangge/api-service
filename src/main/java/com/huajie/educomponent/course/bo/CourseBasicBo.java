package com.huajie.educomponent.course.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import java.util.Date;

/**
 * Created by fangxing on 17-7-11.
 */
@Api("课程")
@Data
public class CourseBasicBo {
    @ApiParam("课程标识")
    private String courseId;
    @ApiParam("课程名称")
    private String courseName;
    @ApiParam("封面")
    private String thumbnailId;
    @ApiParam("主分类")
    private String mainCategoryId;
    @ApiParam("次分类")
    private String subCategoryId;
    @ApiParam("第三级分类。预留")
    private String detailCategoryId;
    private Integer chapterType;
    @ApiParam("课程介绍")
    private String descriptions;
    @ApiParam("开始时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date startTime;
    @ApiParam("结束时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date endTime;
    @ApiParam("课程讲师")
    private String teacherId;
    @ApiParam("课程讲师名称")
    private String teacherName;
    @ApiParam("以下划线为分割符的字符串，查询关键字")
    private String tags;
    @ApiParam("是否公开")
    private Boolean isPublic;
    @ApiParam("上架状态")
    private Boolean isOnShelves;
    @ApiParam("审批状态")
    private int approveStatus;
    @ApiParam("课程被访问次数")
    private Integer accessCount;
    @ApiParam("课程视频总时长")
    private int courseSecond;
    @ApiParam("积分")
    private int score;
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
    @ApiParam("封面图路径")
    private String thumbnailPath;
    @ApiParam("课时")
    private float classHour;
}
