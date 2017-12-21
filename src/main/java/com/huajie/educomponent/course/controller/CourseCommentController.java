package com.huajie.educomponent.course.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.course.bo.CourseCommentBo;
import com.huajie.educomponent.course.service.CourseCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 10070 on 2017/7/19.
 */
@Api("课程评价")
@RestController
@RequestMapping("/course/comment")
public class CourseCommentController {

    @Autowired
    private CourseCommentService courseCommentService;

    @ApiOperation("新建课程评价")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseRetBo saveComment(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                 @RequestBody CourseCommentBo courseCommentBo) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            CourseCommentBo courseComment = courseCommentService.createUserComment(userId, courseCommentBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), courseComment);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("查询课程评价")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseRetBo getComments(@RequestParam(value = "courseId") String courseId,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<CourseCommentBo> result = courseCommentService.search(courseId, page, size);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
}
