package com.huajie.educomponent.pubrefer.bo;

import lombok.Data;
import java.util.List;

/**
 * Created by fangxing on 17-7-6.
 */
@Data
public class CourseCategoryTreeNodeBo extends CourseCategoryNodeBo {

    private List<CourseCategoryTreeNodeBo> children;
}
