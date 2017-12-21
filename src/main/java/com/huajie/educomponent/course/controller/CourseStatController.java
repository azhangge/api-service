package com.huajie.educomponent.course.controller;

import com.huajie.appbase.BaseRetBo;
import com.huajie.appbase.BaseRetMessage;
import com.huajie.appbase.BaseRetType;
import com.huajie.appbase.BusinessException;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.course.bo.CourseChapterStudyBo;
import com.huajie.educomponent.course.bo.CourseChapterStudyDetailBo;
import com.huajie.educomponent.course.service.CourseStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/stat")
public class CourseStatController {

    @Autowired
    private CourseStatService courseStatService;

    //更新用户视频播放进度
    @RequestMapping(value = "/update/study", method = RequestMethod.POST)
    public BaseRetBo updateUserStudyTime(@CookieValue(name = "userId", defaultValue = EnvConstants.userId) String userId,
                                         @RequestBody CourseChapterStudyBo courseChapterStudyBo){

        BaseRetBo retBo = new BaseRetBo();
        try {
            courseStatService.saveChapterStudy(userId, courseChapterStudyBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    //获取用户视频播放进度
    @RequestMapping(value = "/chapter/detail", method = RequestMethod.POST)
    public BaseRetBo getUserStudyTime(@CookieValue(name = "userId", defaultValue = EnvConstants.userId) String userId,
                                      @RequestParam(value = "chapterId") String chapterId){

        BaseRetBo retBo = new BaseRetBo();
        try {
            CourseChapterStudyDetailBo result = courseStatService.getChapterStudy(userId, chapterId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
}
