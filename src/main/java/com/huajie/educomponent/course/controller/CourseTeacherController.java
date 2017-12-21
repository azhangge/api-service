package com.huajie.educomponent.course.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.course.bo.*;
import com.huajie.educomponent.course.service.CourseApproveService;
import com.huajie.educomponent.course.service.CourseQuestionSetService;
import com.huajie.educomponent.course.service.CourseService;
import com.huajie.educomponent.testpaper.bo.QuestionSetBriefBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 10070 on 2017/7/18.
 */
@Api("讲师课程接口")
@RestController
@RequestMapping("/course")
public class CourseTeacherController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseApproveService courseApproveService;

    @Autowired
    private CourseQuestionSetService courseQuestionSetService;

    @ApiOperation("新增、更新课程（基本数据）")
    @RequestMapping(value = "/{isWithRequest}", method = RequestMethod.POST)
    public BaseRetBo save(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                          @PathVariable(value = "isWithRequest") String isWithRequest,
                          @RequestBody CourseBo basicBo) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            CourseCreateBo result = courseService.saveCourse(userId, isWithRequest, basicBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("新增、更新课程章节")
    @RequestMapping(value = "/chapter", method = RequestMethod.POST)
    public BaseRetBo saveChapter(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                 @RequestBody CourseChapterBo chapterBo) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            CourseChapterBo course = courseService.saveChapter(userId, chapterBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), course);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("查询课程（讲师状态课程）")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseRetBo listPages(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                               @RequestParam(value = "approveStatus", required = false) Integer approveStatus) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            List<CourseBasicBo> courseBasicBoList = courseService.search(userId, approveStatus);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), courseBasicBoList);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("删除课程")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public BaseRetBo deleteCourse(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                  @RequestParam(value = "courseIds") List<String> courseIds) throws Exception {
        BaseRetBo retBo = new BaseRetBo();
        try {
            courseService.delete(userId, courseIds);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("删除课程章节")
    @RequestMapping(value = "/chapter", method = RequestMethod.DELETE)
    public BaseRetBo deleteChapter(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                   @RequestParam(value = "chapterId") String chapterId) throws Exception {

        BaseRetBo retBo = new BaseRetBo();
        try {
            courseService.deleteChapter(userId, chapterId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("保存课程附件")
    @RequestMapping(value = "/attachment", method = RequestMethod.POST)
    public BaseRetBo attachment(@RequestBody CourseAttachmentBo attachmentBo) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            CourseAttachmentBo result = courseService.saveAttachment(attachmentBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("上架")
    @RequestMapping(value = "/shelve", method = RequestMethod.POST)
    public BaseRetBo shelve(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                            @RequestBody List<String> courseIds) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            courseService.shelve(userId, courseIds);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("撤回")
    @RequestMapping(value = "/rollback", method = RequestMethod.PUT)
    public BaseRetBo rollBack(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                              @RequestBody List<String> courseIds) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            courseService.rollback(userId, courseIds);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
    @ApiOperation( "根据课程id查询课程详情（包含chpters）")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public BaseRetBo getCourseDetail(String userId,String courseId) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            CourseBo result=courseService.getDetail(courseId, userId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(),result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("获取所有课程")
    @RequestMapping(value = "/all_course", method = RequestMethod.GET)
    public BaseRetBo getCourses(@RequestParam(value = "queryKeyword", required = false) String queryKeyword,
                                @RequestParam(value = "mainCategoryId", required = false) String mainCategoryId,
                                @RequestParam(value = "subCategoryId", required = false) String subCategoryId,
                                @RequestParam(value = "order", defaultValue = "1") Integer order,
                                @RequestParam(value = "orderKey", defaultValue = "0") Integer orderKey,
                                @RequestParam(value = "maxScore", required = false) Integer maxScore,
                                @RequestParam(value = "minScore", required = false) Integer minScore,
                                @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        BaseRetBo retBo = new BaseRetBo();
        try {

            PageResult<CourseBasicBo> result = courseService.courseList(queryKeyword,mainCategoryId,subCategoryId,null,null,orderKey,order,maxScore,minScore,pageIndex,pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("提交审批")
    @RequestMapping(value = "/submitApprove", method = RequestMethod.GET)
    public BaseRetBo submitApprove(@RequestParam(value = "courseId", required = true) String courseId,
                                   @CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            userId = null;
            courseApproveService.submitApprove(courseId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("获取课程题集列表")
    @RequestMapping(value = "/questionSet/list", method = RequestMethod.GET)
    public BaseRetBo getCourseQuestionSet(@RequestParam(value = "courseId") String courseId,
                                          @CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            List<QuestionSetBriefBo> result = courseQuestionSetService.getCourseQuestionSet(courseId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
}
