package com.huajie.educomponent.course.bo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import java.util.List;

/**
 * Created by fangxing on 17-7-11.
 */
@Api("课程章节")
@Data
public class CourseChapterBo {
    @ApiParam("课程章节标识")
    private String chapterId;
    @ApiParam("课程标识")
    private String courseId;
    @ApiParam("章节名称")
    private String name;
    @ApiParam("章节介绍")
    private String descriptions;
    @ApiParam("标签，关键字")
    private String tags;
    @ApiParam("课件时长 单位秒")
    private int second;
    @ApiParam("章节，第几章")
    private Integer chapterIndex;
    @ApiParam("课件标识，视频、pdf之类与课程配套的附件")
    private String resourceId;
    @ApiParam("课件标识，视频、pdf之类与课程配套的附件")
    private String resourcePath;
    @ApiParam("课件标识，视频、pdf之类与课程配套的html附件")
    private String resourceHtmlPath;
    @ApiParam("资源类型")
    private Integer resourceType;
    @ApiParam("parentId为空表示是章，否则表示为节")
    private String parentId;
    @ApiParam("子类")
    private List<CourseChapterBo> children;
    private long fileSize;

}
