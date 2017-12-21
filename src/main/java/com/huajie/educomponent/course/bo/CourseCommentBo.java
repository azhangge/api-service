package com.huajie.educomponent.course.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import java.util.Date;

/**
 * Created by fangxing on 17-7-13.
 */
@Api("课程评论")
@Data
public class CourseCommentBo {

    @ApiParam("课程标识")
    private String courseId;
    @ApiParam("评论人给课程的评价等级")
    private int star;
    @ApiParam("评论人名称")
    private String userName;
    @ApiParam("评论")
    private String comment;
    @ApiParam("评论创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String userFilePath;//评论人头像

}
