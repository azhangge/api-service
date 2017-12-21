package com.huajie.educomponent.pubrefer.bo;

import io.swagger.annotations.Api;
import lombok.Data;

/**
 * Created by fangxing on 17-7-24.
 */
@Api("课程分类")
@Data
public class CourseCategoryNodeBo {

    private String courseCategoryId;
    private String name;
    private String code;
    private String descriptions;
    private String appIcon;
    private String appIconPath;
    private String parentId;
    private String id;
}
