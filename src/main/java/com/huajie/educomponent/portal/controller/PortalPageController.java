package com.huajie.educomponent.portal.controller;


import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.course.bo.*;
import com.huajie.educomponent.course.constants.CourseSortType;
import com.huajie.educomponent.course.constants.IndexType;
import com.huajie.educomponent.course.service.CourseCommentService;
import com.huajie.educomponent.course.service.CourseService;
import com.huajie.educomponent.course.service.CourseStatService;
import com.huajie.educomponent.portal.service.AdPromptService;
import com.huajie.educomponent.portal.service.PortalPageService;
import com.huajie.educomponent.pubrefer.bo.CourseCategoryTreeNodeBo;
import com.huajie.educomponent.pubrefer.service.CourseCategoryService;
import com.huajie.educomponent.testpaper.bo.QuestionSetBo;
import com.huajie.educomponent.testpaper.bo.QuestionSetBriefBo;
import com.huajie.educomponent.testpaper.service.QuestionSetService;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by wangd on 2017/9/17.
 */
@Controller
public class PortalPageController {

    @Autowired
    private PortalPageService portalPageService;

    @Autowired
    private AdPromptService adPromptService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseCategoryService courseCategoryService;

    @Autowired
    private QuestionSetService questionSetService;

    @Autowired
    private CourseCommentService courseCommentService;

    @Autowired
    private CourseStatService courseStatService;


    @RequestMapping("/")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("page/home");
        modelAndView.addObject("title", "华杰学习平台");
        modelAndView.addObject("news", portalPageService.getCourseData(IndexType.NEWCOURSE.getValue(), CourseSortType.NEWEST.getValue()));
        modelAndView.addObject("hots", portalPageService.getCourseData(IndexType.HOTCOURSE.getValue(), CourseSortType.HOTEST.getValue()));
        //modelAndView.addObject("teachers", portalPageService.getHotTeacher());
        modelAndView.addObject("categoryCourses", portalPageService.getCourseCategoryTree());
        modelAndView.addObject("courses", adPromptService.get(true)); //   轮播图课程
        modelAndView.addObject("categories", portalPageService.getCourseCategoryTree());
        return modelAndView;
    }

    @RequestMapping("/courses")
    public ModelAndView courseTest(HttpRequest request, HttpResponse response,
                                   @RequestParam(value = "queryKeyword", required = false) String queryKeyword,
                                   @RequestParam(value = "mainCategoryId", required = false) String mainCategoryId,
                                   @RequestParam(value = "subCategoryId", required = false) String subCategoryId,
                                   @RequestParam(value = "isPublic", required = false, defaultValue = "true") Boolean isPublic,
                                   @RequestParam(value = "onShelves", required = false, defaultValue = "true") Boolean onShelves,
                                   @RequestParam(value = "order", required = false, defaultValue = "1") Integer order,
                                   @RequestParam(value = "orderKey", required = false, defaultValue = "0") Integer orderKey,
                                   @RequestParam(value = "maxScore", required = false) Integer maxScore,
                                   @RequestParam(value = "minScore", required = false) Integer minScore,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        PageResult<CourseBasicBo> result = courseService.courseList(queryKeyword, mainCategoryId, subCategoryId, isPublic, onShelves, orderKey, order, maxScore, minScore, pageIndex, pageSize);
        List<CourseCategoryTreeNodeBo> categoryTree = courseCategoryService.getCourseCategoryTree();
        ModelAndView modelAndView = new ModelAndView("page/courselist");
        modelAndView.addObject("result", result);
        modelAndView.addObject("categoryTree", categoryTree);
        return modelAndView;
    }

    @RequestMapping(value = "/course/{courseId}", method = RequestMethod.GET)
    public ModelAndView courseTest(HttpRequest request, HttpResponse response, @PathVariable String courseId,
                                   @CookieValue(value="userId", required = false)String userId) {
        CourseBo courseBo = courseService.getDetail(courseId, userId);
        PageResult<CourseCommentBo> comments = courseCommentService.search(courseId, 0, 10);
        ModelAndView modelAndView = new ModelAndView("page/coursedetail");
        modelAndView.addObject("course", courseBo);
        modelAndView.addObject("comments",comments);
        return modelAndView;
    }

    @RequestMapping(value = "/course/{courseId}/{coursewareid}", method = RequestMethod.GET)
    public ModelAndView viewCourseware(HttpRequest request, HttpResponse response,
                                       @CookieValue(name = "userId", defaultValue = EnvConstants.userId) String userId,
                                       @PathVariable String courseId,
                                       @PathVariable String coursewareid,
                                       @RequestParam(value = "type") Integer type) {
        ModelAndView modelAndView = new ModelAndView();
        if (type == 2 || type == 3) {
            modelAndView.setViewName("page/courseviewvedio");
        }else if (type == 4 || type == 5 || type == 6){
            modelAndView.setViewName("page/courseviewhtml");
        }else {
            throw new BusinessException("参数错误");
        }
        CourseBo courseBo = courseService.getDetail(courseId, null);
        modelAndView.addObject("course", courseBo);
        for (CourseChapterBo courseChapterBo : courseBo.getChapters()) {
            if (courseChapterBo.getChildren() != null) {
                for (CourseChapterBo childrenBo : courseChapterBo.getChildren()) {
                    if (!StringUtils.isEmpty(childrenBo.getResourceId()) && childrenBo.getResourceId().equals(coursewareid)) {
                        modelAndView.addObject("courseWarePath", childrenBo.getResourcePath());
                        modelAndView.addObject("resourceHtmlPath",childrenBo.getResourceHtmlPath());
                        break;
                    }
                }
            }
        }
        CourseChapterStudyDetailBo studyDetail = courseStatService.getChapterStudy(userId, coursewareid);
        if (studyDetail != null && studyDetail.getLastStudyTime() != null){
            modelAndView.addObject("lastStudyTime", studyDetail.getLastStudyTime());
        }else {
            modelAndView.addObject("lastStudyTime", 0);
        }
        modelAndView.addObject("coursewareid", coursewareid);
        return modelAndView;
    }


    @RequestMapping(value = "/questionsets", method = RequestMethod.GET)
    public ModelAndView listPages(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                  @RequestParam(value = "search", required = false) String search,
                                  @RequestParam(value = "type", required = false) Integer type,
                                  @RequestParam(value = "self", required = false, defaultValue = "false") Boolean self,
                                  @RequestParam(value = "order", required = false, defaultValue = "1") Integer orderBy,
                                  @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws Exception {

        ModelAndView view = new ModelAndView("page/questionsets");
        PageResult<QuestionSetBriefBo> result = questionSetService.listPages(userId,null, search, type, self, orderBy, pageIndex, pageSize);
        view.addObject("result", result);
        return view;
    }

    @RequestMapping(value = "/questionsets/{questionsetid}", method = RequestMethod.GET)
    public ModelAndView startTest(@CookieValue(value = "userId", defaultValue = "40283f815e0416df015e047a6026000c") String userId,
                                  @PathVariable(value = "questionsetid") String questionSetId) {

        QuestionSetBo result = questionSetService.getDetails(userId, questionSetId);
        ModelAndView view = new ModelAndView("page/questionsetdetail");
        view.addObject("result", result);
        return view;
    }
}
