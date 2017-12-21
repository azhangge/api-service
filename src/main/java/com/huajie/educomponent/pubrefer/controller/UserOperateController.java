package com.huajie.educomponent.pubrefer.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.course.bo.CourseBasicBo;
import com.huajie.educomponent.pubrefer.bo.UserOperateBo;
import com.huajie.educomponent.pubrefer.service.UserOperateService;
import com.huajie.educomponent.usercenter.service.CheckAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 10070 on 2017/7/19.
 */
@Api("用户操作")
@RestController
@RequestMapping("/user_operate")
public class UserOperateController {

    @Autowired
    private UserOperateService userOperateService;
  
    @Autowired
    private CheckAccountService checkAccountService;

    @ApiOperation("学员操作（课程收藏、课程购买、加入学习、取消课程题目收藏、取消学习、最近访问、题目收藏、试卷收藏、试卷取消收藏）")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseRetBo userOperate(@CookieValue(value="userId", defaultValue = EnvConstants.userId)String userId,
                                 @RequestBody UserOperateBo operateBo){

        BaseRetBo retBo = new BaseRetBo();



        if (operateBo.getObjectId() == null){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.ARGUMENT_ERROR.getValue());
        }
        try {
            checkAccountService.checkUserToken(userId);
            boolean result = userOperateService.operate(userId, operateBo);
            if (result == true){
                return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
            }else {
                return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.FAILED.getValue());
            }
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("查询")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseRetBo getUserCourseEnd(@CookieValue(value="userId", defaultValue = EnvConstants.userId)String userId,
                                      @RequestParam(value = "type") Integer type,
                                      @RequestParam(value = "timeOut", required = false, defaultValue = "false") Boolean timeOut,
                                      @RequestParam(value = "pageIndex" ,defaultValue = "0") int page,
                                      @RequestParam(value = "pageSize" ,defaultValue = "10") int size){

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<Object> result = userOperateService.getUserOperate(userId, type, timeOut, page, size);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
}
