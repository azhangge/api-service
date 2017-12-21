package com.huajie.educomponent.exam.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.exam.bo.enroll.ExamEnrollBo;
import com.huajie.educomponent.exam.bo.enroll.ExamEnrollCreateBo;
import com.huajie.educomponent.exam.bo.enroll.ExamUserEnrollBo;
import com.huajie.educomponent.exam.bo.enroll.ExamUserEnrollDetailBo;
import com.huajie.educomponent.exam.service.ExamEnrollService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zgz on 2017/9/11.
 */
@RestController
@RequestMapping("/enroll")
public class EnrollController {

    @Autowired
    private ExamEnrollService examEnrollService;

    @ApiOperation("报名 type 1 报名， 2 取消报名")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseRetBo enroll(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                            @RequestBody ExamEnrollCreateBo enrollCreateBo){

        BaseRetBo retBo = new BaseRetBo();
        try {
            examEnrollService.enroll(userId, enrollCreateBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }

    }

    @ApiOperation("用户考试日程")
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public BaseRetBo userEnroll(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize){

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<ExamEnrollBo> result = examEnrollService.search(null, userId, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("管理员考试报名查询")
    @RequestMapping(value = "/manage/list", method = RequestMethod.GET)
    public BaseRetBo enroll(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                            @RequestParam(value = "examId") String examId,
                            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize){

        BaseRetBo retBo = new BaseRetBo();
        try {
            ExamUserEnrollDetailBo result = examEnrollService.getExamUserEnroll(examId, null, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }


}
