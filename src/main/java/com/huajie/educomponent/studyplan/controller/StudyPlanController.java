package com.huajie.educomponent.studyplan.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.studyplan.bo.StudyPlanBasicBo;
import com.huajie.educomponent.studyplan.bo.StudyPlanCreateBo;
import com.huajie.educomponent.studyplan.service.StudyPlanService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 学习计划
 * Created by xuxiaolong on 2017/10/31.
 */
@Controller
@RequestMapping("/studyPlan")
public class StudyPlanController {

    @Autowired
    private StudyPlanService studyPlanService;

    @ApiOperation("创建/修改学习计划")
    @RequestMapping(value = "/addOrUpd", method = RequestMethod.POST)
    public BaseRetBo save(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                          @RequestBody StudyPlanCreateBo studyPlanCreateBo){

        BaseRetBo retBo = new BaseRetBo();
        try {
            StudyPlanBasicBo result = studyPlanService.save(userId, studyPlanCreateBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("删除学习计划")
    @RequestMapping(value = "/{planId}", method = RequestMethod.DELETE)
    public BaseRetBo delete(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                            @RequestParam(value = "planId") String planId){

        BaseRetBo retBo = new BaseRetBo();
        try {
            studyPlanService.deleteStudyPlan(userId,planId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("管理员查看学习计划列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseRetBo getStudyPlans(@RequestParam(value = "isPublish",required = false) String isPublish,
                                   @RequestParam(value = "planName",required = false) String planName,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize){

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<StudyPlanCreateBo> result = studyPlanService.getStudyPlans(isPublish,planName,pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("用户查看学习计划列表")
    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public BaseRetBo getUserStudyPlans(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                       @RequestParam(value = "isPublish",required = false) String isPublish,
                                       @RequestParam(value = "planName",required = false) String planName,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize){

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<StudyPlanCreateBo> result = studyPlanService.getUserStudyPlans(userId,isPublish,planName,pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
}