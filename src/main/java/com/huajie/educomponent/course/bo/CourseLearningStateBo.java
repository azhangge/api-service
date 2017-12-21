package com.huajie.educomponent.course.bo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import java.util.Date;

/**
 * Created by zhanggz on 2017/7/19.
 */
@Api("学习进度")
@Data
public class CourseLearningStateBo {
    @ApiParam("课程标识")
    private String courseId;
    @ApiParam("学员")
    private String userId;
    @ApiParam("资源标识")
    private String resourceId;
    @ApiParam("上次进度 百分数")
    private int startPoint;//上次的进度
    @ApiParam("上次学习时间")
    private Date lastTime;
}
