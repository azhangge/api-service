package com.huajie.educomponent.portal.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.course.bo.CourseBasicBo;
import com.huajie.educomponent.course.bo.CourseBo;
import com.huajie.educomponent.course.service.CourseService;
import com.huajie.educomponent.portal.bo.PortalCourseBo;
import com.huajie.educomponent.portal.service.PortalService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zgz on 2017/8/10.
 */
@RestController
@RequestMapping("/portal")
public class PortalController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private PortalService portalService;

    @ApiOperation("首页课程聚合接口")
    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public BaseRetBo getPortalCourses(@CookieValue(value="userId", defaultValue = EnvConstants.userId)String userId,
                                      @RequestParam(value = "pageIndex", required = false, defaultValue = "0")int pageIndex,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "4")int pageSize){

        BaseRetBo retBo = new BaseRetBo();
        try {
            PortalCourseBo result = portalService.getPortalCourses(userId, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("获取所有课程(包括最新、最热排序、类别等)")
    @RequestMapping(value = "/all_course", method = RequestMethod.GET)
    public BaseRetBo getCourses(@RequestParam(value = "queryKeyword", required = false) String queryKeyword,
                                @RequestParam(value = "mainCategoryId", required = false) String mainCategoryId,
                                @RequestParam(value = "subCategoryId", required = false) String subCategoryId,
                                @RequestParam(value = "isPublic", required = false, defaultValue = "true") Boolean isPublic,
                                @RequestParam(value = "onShelves", required = false, defaultValue = "true") Boolean onShelves,
                                @RequestParam(value = "order", defaultValue = "1") Integer order,
                                @RequestParam(value = "orderKey", defaultValue = "0") Integer orderKey,
                                @RequestParam(value = "maxScore", required = false) Integer maxScore,
                                @RequestParam(value = "minScore", required = false) Integer minScore,
                                @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        BaseRetBo retBo = new BaseRetBo();
        try {

            PageResult<CourseBasicBo> result = courseService.courseList(queryKeyword,mainCategoryId,subCategoryId,isPublic,onShelves,orderKey,order,maxScore,minScore,pageIndex,pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("课程详情")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseRetBo getDetail(@CookieValue(value="userId", defaultValue = EnvConstants.userId)String userId,
                               @RequestParam(value = "courseId") String courseId){

        BaseRetBo retBo = new BaseRetBo();
        try {
            CourseBo courseBo = courseService.getDetail(courseId, userId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), courseBo);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        }catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("推荐课程")
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public BaseRetBo recommend(){

        BaseRetBo retBo = new BaseRetBo();
        try {
            List<CourseBasicBo> pageResult = courseService.getRecommend();
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), pageResult);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
}
