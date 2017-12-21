package com.huajie.educomponent.exam.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.exam.bo.notice.ExamNoticeBo;
import com.huajie.educomponent.exam.bo.notice.ExamNoticeBriefUserBo;
import com.huajie.educomponent.exam.bo.notice.ExamNoticeCreateBo;
import com.huajie.educomponent.exam.service.ExamNoticeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

/**
 * Created by zgz on 2017/9/7.
 */
@RestController
@RequestMapping("/notice/exam")
public class ExamNoticeController {

    @Autowired
    private ExamNoticeService examNoticeService;

    @ApiOperation("创建/更新通知(考试)")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseRetBo save(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                          @RequestBody ExamNoticeCreateBo noticeCreateBo){

        BaseRetBo retBo = new BaseRetBo();
        try {
            ExamNoticeBo result = examNoticeService.save(userId, noticeCreateBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("发布通知")
    @RequestMapping(value = "/push", method = RequestMethod.PUT)
    public BaseRetBo push(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                          @RequestParam(value = "examId") String examId){

        BaseRetBo retBo = new BaseRetBo();
        try {
            examNoticeService.pushNotice(userId, examId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("用户获取通知（考试）列表")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public BaseRetBo getUserExamNotices(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                        @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize){

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<ExamNoticeBriefUserBo> result = examNoticeService.getUserExamNotices(userId, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("管理员查看通知（考试）")
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public BaseRetBo getExamNotices(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "type", required = false) Integer type,
                                    @RequestParam(value = "beginTime", required = false) Date beginTime,
                                    @RequestParam(value = "endTime", required = false) Date endTime,
                                    @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize){

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<ExamNoticeBo> result = examNoticeService.getExamNotices(name, type, beginTime, endTime, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("获取通知详情")
    @RequestMapping(value = "/{examId}", method = RequestMethod.GET)
    public BaseRetBo getDetails(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                @PathVariable(value = "examId") String examId){

        BaseRetBo retBo = new BaseRetBo();
        try {
            ExamNoticeBo result = examNoticeService.getDetails(userId, examId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("管理员删除通知")
    @RequestMapping(value = "/{examId}", method = RequestMethod.DELETE)
    public BaseRetBo delete(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                            @PathVariable(value = "examId") String examId){

        BaseRetBo retBo = new BaseRetBo();
        try {
            examNoticeService.delete(examId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

}
