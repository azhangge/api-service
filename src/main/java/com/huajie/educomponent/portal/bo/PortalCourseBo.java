package com.huajie.educomponent.portal.bo;

import com.huajie.educomponent.pubrefer.bo.CourseCategoryNodeBo;
import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanggz on 2017/8/18.
 */
@Data
public class PortalCourseBo {

    private List<CourseCategoryNodeBo> hotMainCategory;
    private List<CategoryCourseBo> hotSubCategoryCourses;
    private List<PortalBriefCourseBo> newestCourses;
    private List<PortalBriefCourseBo> hotCourses;
    private String courseItemName;
    private List<PortalBriefCourseBo>  indexCourses; // for  web首页的分类课程
    private List<CourseCategoryNodeBo> subCategories; // for web首页二级分类
    private String mainCategoryName; // for web首页一级分类
    private String mainCategoryId;
}
