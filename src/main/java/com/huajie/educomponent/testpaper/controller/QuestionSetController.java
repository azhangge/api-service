package com.huajie.educomponent.testpaper.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.testpaper.bo.QuestionSetBo;
import com.huajie.educomponent.testpaper.bo.QuestionSetBriefBo;
import com.huajie.educomponent.testpaper.bo.QuestionSetCreateBo;
import com.huajie.educomponent.testpaper.service.QuestionSetService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 10070 on 2017/7/24.
 */
@RestController
@RequestMapping("/test_paper")
public class QuestionSetController {

    @Autowired
    private QuestionSetService questionSetService;

    @ApiOperation("新增、更新题集试卷")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseRetBo saveBrief(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                               @RequestBody QuestionSetCreateBo createBo){

        BaseRetBo retBo = new BaseRetBo();
        try {
            QuestionSetCreateBo result = questionSetService.saveQuestionSet(userId, createBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("题集试卷模板详情")
    @RequestMapping(value = "/{questionSetId}", method = RequestMethod.GET)
    public BaseRetBo get(@CookieValue(value = "userId", defaultValue = EnvConstants.userId)String userId,
                         @PathVariable(value = "questionSetId") String questionSetId){

        BaseRetBo retBo = new BaseRetBo();
        try {
            QuestionSetBo result = questionSetService.getDetails(userId, questionSetId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("查询题集试卷")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseRetBo listPages(@CookieValue(value = "userId", defaultValue = EnvConstants.userId)String userId,
                               @RequestParam(value = "isPublic" ,required = false)Boolean isPublic,
                               @RequestParam(value = "search" ,required = false) String search,
                               @RequestParam(value = "type" ,required = false) Integer type,
                               @RequestParam(value = "self" ,required = false, defaultValue = "false") Boolean self,
                               @RequestParam(value = "order" ,required = false, defaultValue = "1") Integer orderBy,
                               @RequestParam(value = "pageIndex" ,defaultValue = "0") int pageIndex,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<QuestionSetBriefBo> result = questionSetService.listPages(userId, isPublic, search, type, self, orderBy, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("删除题集试卷")
    @RequestMapping(value = "/{questionSetId}", method = RequestMethod.DELETE)
    public BaseRetBo delete(@PathVariable(value = "questionSetId")String questionSetId) throws Exception {

        BaseRetBo retBo = new BaseRetBo();
        try {
            questionSetService.delete(questionSetId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("搜寻题集试卷")
    @RequestMapping(value = "/searchQuestionSet", method = RequestMethod.GET)
    public BaseRetBo searchQuestionSet(@CookieValue(value = "userId", defaultValue = EnvConstants.userId)String userId,
                               @RequestParam(value = "search" ,required = false) String search,
                               @RequestParam(value = "type" ,required = false) Integer type,
                               @RequestParam(value = "source" , defaultValue = "3") Integer source,
                               @RequestParam(value = "order" ,required = false, defaultValue = "1") Integer orderBy,
                               @RequestParam(value = "pageIndex" ,defaultValue = "0") int pageIndex,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<QuestionSetBriefBo> result = questionSetService.searchQuestionSet(userId, search, source, type,  orderBy, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
}
