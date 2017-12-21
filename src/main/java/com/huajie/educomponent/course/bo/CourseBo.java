package com.huajie.educomponent.course.bo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import java.util.List;

/**
 * Created by fangxing on 17-7-11.
 */
@Api("")
@Data
public class CourseBo extends CourseBasicBo {
    @ApiParam("附件id")
    private String courseAttachmentId;
    private String courseAttachmentName;
    private String courseAttachmentPath;
    @ApiParam("练习")
    private String exerciseId;
    @ApiParam("子章节")
    private List<CourseChapterBo> chapters;

    private List<String> deleteIds;//批量删除章节
    private List<CourseChapterBo> changeChapterBos;//影响到的章节

    private CourseQuestionSetBo courseQuestionSetBo;

    private int isFavorite;
    private int isBuy;
    private Integer isJoin;
}
