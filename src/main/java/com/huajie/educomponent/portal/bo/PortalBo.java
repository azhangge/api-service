package com.huajie.educomponent.portal.bo;

import com.huajie.educomponent.pubrefer.bo.CourseCategoryNodeBo;
import lombok.Data;
import java.util.List;

/**
 * Created by Lenovo on 2017/8/18.
 */
@Data
public class PortalBo {

    private List<CourseCategoryNodeBo> hotMainCategory;
    private List<CategoryCourseBo> hotSubCategoryCourses;
    private List<PortalCourseBo> newestCourses;
    private List<PortalCourseBo> hotCourses;
}
