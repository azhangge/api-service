package com.huajie.educomponent.studyplan.bo;

import com.huajie.educomponent.course.bo.CourseBo;
import com.huajie.educomponent.questionbank.bo.QuestionBo;
import com.huajie.educomponent.usercenter.bo.UserBasicInfoBo;
import lombok.Data;

import java.util.List;

/**
 * Created by xuxiaolong on 2017/10/31.
 */
@Data
public class StudyPlanBo extends StudyPlanBasicBo {
    private String thumbnailPath;//缩略图路径
    private List<CourseBo> courseBoList;
    private List<QuestionBo> questionBoList;
    private List<UserBasicInfoBo> userBasicInfoBoList;
}
