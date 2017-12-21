package com.huajie.educomponent.pubrefer.bo;

import lombok.Data;
import java.util.List;

/**
 * Created by zgz on 2017/8/21.
 */
@Data
public class CourseCategoryHotBo {

    private List<CourseCategoryNodeBo> mainCategory;
    private List<CourseCategoryNodeBo> subCategory;
    private String categoryName;
}
