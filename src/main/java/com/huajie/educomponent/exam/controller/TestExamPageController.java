package com.huajie.educomponent.exam.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.exam.bo.answer.exam.ExamUserAnswerBriefBo;
import com.huajie.educomponent.exam.bo.answer.test.TestQuestionSetInsBo;
import com.huajie.educomponent.exam.bo.answer.test.TestUserAnswerBriefBo;
import com.huajie.educomponent.exam.bo.enroll.ExamEnrollBo;
import com.huajie.educomponent.exam.bo.exam.ExamQuestionSetBo;
import com.huajie.educomponent.exam.bo.notice.ExamNoticeBo;
import com.huajie.educomponent.exam.bo.notice.ExamNoticeBriefUserBo;
import com.huajie.educomponent.exam.service.ExamEnrollService;
import com.huajie.educomponent.exam.service.ExamNoticeService;
import com.huajie.educomponent.exam.service.TestExamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class TestExamPageController {

    @Autowired
    private TestExamService testExamService;

    @Autowired
    private ExamNoticeService examNoticeService;

    @Autowired
    private ExamEnrollService examEnrollService;

    @ApiOperation("开始考试")
    @RequestMapping(value = "/questionset/exam/{examId}", method = RequestMethod.GET)
    public ModelAndView startExam(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                  @PathVariable(value = "examId") String examId) {

        ExamQuestionSetBo result = testExamService.startExam(userId, examId);
        ModelAndView view = new ModelAndView("page/exam");
        view.addObject("result",result);
        return view;
    }

    //练习作答记录列表
    @RequestMapping(value = "/test/answer/list", method = RequestMethod.GET)
    public ModelAndView getTest(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        PageResult<TestUserAnswerBriefBo> result = testExamService.getTestList(userId, pageIndex, pageSize);
        ModelAndView view = new ModelAndView("page/userprictiselist");
        view.addObject("result", result);
        return view;
    }


    //用户获取通知（考试）列表
    @RequestMapping(value = "/notice/user/list", method = RequestMethod.GET)
    public ModelAndView getUserExamNotices(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                           @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        PageResult<ExamNoticeBriefUserBo> result = examNoticeService.getUserExamNotices(userId, pageIndex, pageSize);
        ModelAndView view = new ModelAndView("page/userexamnoticelist");
        view.addObject("result", result);
        return view;
    }

    //通知详情
    @ResponseBody
    @RequestMapping(value = "/notice/exam/detail/{examId}", method = RequestMethod.GET)
    public ModelAndView getDetails(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                   @PathVariable(value = "examId") String examId) {

        ExamNoticeBo result = examNoticeService.getDetails(userId, examId);
        ModelAndView view = new ModelAndView("page/userexamnoticedetail");
        view.addObject("result", result);
        return view;
    }

    //考试日程
    @RequestMapping(value = "/user/examplan/list", method = RequestMethod.GET)
    public ModelAndView userEnroll(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        PageResult<ExamEnrollBo> result = examEnrollService.search(null, userId, pageIndex, pageSize);
        ModelAndView view = new ModelAndView("page/userexamplan");
        view.addObject("result", result);
        return view;
    }

    //用户考试作答列表
    @RequestMapping(value = "/user/exam/score/list", method = RequestMethod.GET)
    public ModelAndView getExamScoreList(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                         @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        PageResult<ExamUserAnswerBriefBo> result = testExamService.getExamList(userId, pageIndex, pageSize);
        ModelAndView view = new ModelAndView("page/userexamscorelist");
        view.addObject("result", result);
        return view;
    }

    @ApiOperation("查看练习作答详情")
    @RequestMapping(value = "/user/test/answer/{questionSetInsId}", method = RequestMethod.GET)
    public ModelAndView getInsTest(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                   @PathVariable(value = "questionSetInsId") String questionSetInsId) {

        TestQuestionSetInsBo result = testExamService.getTestInsAnswer(userId, questionSetInsId);
        ModelAndView view = new ModelAndView("page/useranswerdetail");
        view.addObject("result", result);
        return view;
    }
}
