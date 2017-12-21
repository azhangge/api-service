package com.huajie.educomponent.course.bo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * Created by fangxing on 17-7-11.
 */

@Api("课程审批")
@Data
public class CourseApproveBo {

    @ApiParam("课程标识")
    private String courseId;
    @ApiParam("4 同意，5 不同意 3开始审批")
    private Integer operate;
    @ApiParam("理由")
    private String reason;

}
