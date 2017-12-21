package com.huajie.educomponent.exam.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.exam.bo.answer.UserSubmitAnswerBo;
import com.huajie.educomponent.exam.bo.answer.exam.ExamUserAnswerBriefBo;
import com.huajie.educomponent.exam.bo.answer.test.TestQuestionSetInsBo;
import com.huajie.educomponent.exam.bo.answer.test.TestUserAnswerBriefBo;
import com.huajie.educomponent.exam.bo.exam.ExamQuestionSetBo;
import com.huajie.educomponent.exam.bo.test.TestQuestionSetBo;
import com.huajie.educomponent.exam.service.TestExamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zgz on 2017/9/7.
 */
@RestController
@RequestMapping("")
public class TestExamController {

    @Autowired
    private TestExamService testExamService;

    @ApiOperation("提交作答(考试、练习共用)")
    @RequestMapping(value = "/test_exam/submit", method = RequestMethod.POST)
    public BaseRetBo submit(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                            @RequestBody UserSubmitAnswerBo userAnswer) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            String insId = testExamService.submit(userId, userAnswer);
            IdRetBo result = new IdRetBo(insId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("开始考试")
    @RequestMapping(value = "/exam/{examId}", method = RequestMethod.GET)
    public BaseRetBo startExam(@CookieValue(value = "userId") String userId,
                               @PathVariable(value = "examId") String examId) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            ExamQuestionSetBo result = testExamService.startExam(userId, examId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("开始练习")
    @RequestMapping(value = "/test/{questionSetId}", method = RequestMethod.GET)
    public BaseRetBo startTest(@CookieValue(value = "userId") String userId,
                               @PathVariable(value = "questionSetId") String questionSetId) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            TestQuestionSetBo result = testExamService.startTest(userId, questionSetId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("用户考试作答列表")
    @RequestMapping(value = "/user/exam/list", method = RequestMethod.GET)
    public BaseRetBo getTest(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0")int pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<ExamUserAnswerBriefBo> result = testExamService.getExamList(userId, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("用户练习作答列表")
    @RequestMapping(value = "/user/test/list", method = RequestMethod.GET)
    public BaseRetBo getExam(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0")int pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<TestUserAnswerBriefBo> result = testExamService.getTestList(userId, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("查看练习作答详情")
    @RequestMapping(value = "/user/test/{questionSetInsId}", method = RequestMethod.GET)
    public BaseRetBo getInsTest(@CookieValue(value = "userId") String userId,
                                @PathVariable(value = "questionSetInsId") String questionSetInsId) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            TestQuestionSetInsBo result = testExamService.getTestInsAnswer(userId, questionSetInsId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

}
