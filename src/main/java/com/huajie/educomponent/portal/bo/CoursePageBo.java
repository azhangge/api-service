package com.huajie.educomponent.portal.bo;

import com.huajie.educomponent.course.bo.CourseBasicBo;
import lombok.Data;

import java.util.List;

/**
 * Created by wangd on 2017/9/27.
 */
@Data
public class CoursePageBo {
    private List<CourseBasicBo> items;  //for web 首页课程列表

}
