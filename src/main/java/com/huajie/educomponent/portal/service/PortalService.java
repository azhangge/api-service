package com.huajie.educomponent.portal.service;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.bo.CourseBasicBo;
import com.huajie.educomponent.course.constants.CourseSortType;
import com.huajie.educomponent.course.service.CourseService;
import com.huajie.educomponent.portal.bo.CategoryCourseBo;
import com.huajie.educomponent.portal.bo.PortalBriefCourseBo;
import com.huajie.educomponent.portal.bo.PortalCourseBo;
import com.huajie.educomponent.pubrefer.bo.CourseCategoryHotBo;
import com.huajie.educomponent.pubrefer.bo.CourseCategoryNodeBo;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.service.CourseCategoryService;
import com.huajie.educomponent.pubrefer.service.FileStorageService;
import com.huajie.utils.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/8/21.
 */
@Service
public class PortalService {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseCategoryService categoryService;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 首页课程数据大拼凑
     * @param userId
     * @return
     */
    public PortalCourseBo getPortalCourses(String userId, int pageIndex, int pageSize){
        PortalCourseBo result = new PortalCourseBo();
        CourseCategoryHotBo categoryHotBo = categoryService.getHotCategoryList(pageSize, pageSize);
        //最热主分类
        result.setHotMainCategory(categoryHotBo.getMainCategory());
        //最热次分类及课程
        result.setHotSubCategoryCourses(getSubCourses(categoryHotBo.getSubCategory(), pageIndex, pageSize));
        //最热课程6个
        PageResult<CourseBasicBo> hotCourse = courseService.getCourseSort(CourseSortType.HOTEST.getValue(), pageIndex, pageSize + 2);
        result.setHotCourses(convertToPortalBriefCourseBoList((List<CourseBasicBo>) hotCourse.getItems()));
        //最新课程6个
        PageResult<CourseBasicBo> newCourse = courseService.getCourseSort(CourseSortType.NEWEST.getValue(), pageIndex, pageSize + 2);
        result.setNewestCourses(convertToPortalBriefCourseBoList((List<CourseBasicBo>) newCourse.getItems()));
        return result;
    }

    /**
     * 获取子类下属的课程
     * @param subCategory
     * @param pageIndex
     * @param pageSize
     * @return
     */
    private List<CategoryCourseBo> getSubCourses(List<CourseCategoryNodeBo> subCategory, int pageIndex, int pageSize) {
        List<CategoryCourseBo> categoryCourseBoList = new ArrayList<CategoryCourseBo>();
        for (CourseCategoryNodeBo categoryNodeBo : subCategory) {
            CategoryCourseBo categoryCourseBo = new CategoryCourseBo();
            BeanUtils.copyProperties(categoryNodeBo, categoryCourseBo);
            PageResult<CourseBasicBo> subCategoryCourses = courseService.courseList(null, null, categoryNodeBo.getCourseCategoryId(), true, true, 1, 1, null, null, pageIndex, pageSize);
            categoryCourseBo.setChildren(convertToPortalBriefCourseBoList((List<CourseBasicBo>) subCategoryCourses.getItems()));
            categoryCourseBoList.add(categoryCourseBo);
        }
        return categoryCourseBoList;
    }

    private List<PortalBriefCourseBo> convertToPortalBriefCourseBoList(List<CourseBasicBo> subCategoryCourses){
        List<PortalBriefCourseBo> portalBriefCourseBoList = new ArrayList<PortalBriefCourseBo>();
        for (CourseBasicBo courseBasicBo:subCategoryCourses){
            PortalBriefCourseBo portalBriefCourseBo = new PortalBriefCourseBo();
            BeanUtils.copyProperties(courseBasicBo, portalBriefCourseBo);
            if (courseBasicBo.getThumbnailId() != null) {
                FileStorageBo file = fileStorageService.getStore(courseBasicBo.getThumbnailId());
                portalBriefCourseBo.setThumbnailPath(PathUtils.getFilePath(file));
            }
            portalBriefCourseBoList.add(portalBriefCourseBo);
        }
        return portalBriefCourseBoList;
    }
}
