package com.huajie.educomponent.pubrefer.bo;

import lombok.Data;

import java.util.List;

@Data
public class CourseCategoryModBo {
    List<CourseCategoryNodeBo> changed;
    List<String> deleted;
}
