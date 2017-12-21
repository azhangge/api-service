package com.huajie.educomponent.exam.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.exam.bo.notice.ExamNoticeBo;
import com.huajie.educomponent.exam.bo.score.ExamScoreBo;
import com.huajie.educomponent.exam.bo.score.ExamUserScoreBo;
import com.huajie.educomponent.exam.service.ExamScoreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zgz on 2017/9/14.
 */
@RestController
@RequestMapping("/score")
public class ExamScoreController {

    @Autowired
    private ExamScoreService examScoreService;

    @ApiOperation("用户个人查看考试成绩（列出所有考试，交卷时间排序,可能某场考试考了多次，都列出了，按分数排序）")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public BaseRetBo getUserScore(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                  @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize){

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<ExamUserScoreBo> result = examScoreService.getUserScore(userId, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("管理员查看某考试成绩情况（列出所有考试记录，按交卷时间排序）")
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public BaseRetBo getScore(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                              @RequestParam(value = "examId") String examId,
                              @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize){

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<ExamScoreBo> result = examScoreService.getExamScore(examId, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
}
