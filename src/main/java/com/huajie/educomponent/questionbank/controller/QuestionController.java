package com.huajie.educomponent.questionbank.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.questionbank.bo.QuestionBo;
import com.huajie.educomponent.questionbank.bo.QuestionBriefBo;
import com.huajie.educomponent.questionbank.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 10070 on 2017/7/21.
 */
@Api("试题")
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @ApiOperation("创建、更新试题")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseRetBo save(@CookieValue(value = "userId", defaultValue = "40283f815e0416df015e047a6026000c")String userId,
                          @RequestBody QuestionBo questionBo){

        BaseRetBo retBo = new BaseRetBo();
        try {
            String questionId = questionService.save(userId, questionBo);
            IdRetBo result = new IdRetBo(questionId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        }catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        }
    }

    @ApiOperation("试题详情")
    @RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
    public BaseRetBo get(@PathVariable(value = "questionId") String questionId){

        BaseRetBo retBo = new BaseRetBo();
        try {
            QuestionBo result = questionService.getOne(questionId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        }
    }

    @ApiOperation("查询试题")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BaseRetBo listPages(@CookieValue(value = "userId", required = false)String userId,
                               @RequestParam(value = "search" ,required = false) String search,
                               @RequestParam(value = "type", required = false) Integer type,
                               @RequestParam(value = "mainCategory", required = false) String mainCategory,
                               @RequestParam(value = "subCategory", required = false) String subCategory,
                               @RequestParam(value = "page" ,defaultValue = "0", required = false) int pageIndex,
                               @RequestParam(value = "size" ,defaultValue = "10", required = false) int pageSize){

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<QuestionBriefBo> result = questionService.listPages(userId, search, type, mainCategory, subCategory, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        }catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        }
    }

    @ApiOperation("删除试题")
    @RequestMapping(value = "/question/{question_id}", method = RequestMethod.DELETE)
    public BaseRetBo delete(@PathVariable(value = "question_id")String questionId) throws Exception {

        BaseRetBo retBo = new BaseRetBo();
        try {
            questionService.delete(questionId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        }catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        }
    }

    @ApiOperation("个人题库(题目引用)")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public BaseRetBo userBanks(@CookieValue(value = "userId", required = false, defaultValue = EnvConstants.userId)String userId,
                               @RequestParam(value = "action", required = false)Integer action,
                               @RequestParam(value = "search" ,required = false) String search,
                               @RequestParam(value = "type", required = false) Integer type,
                               @RequestParam(value = "mainCategory", required = false) String mainCategory,
                               @RequestParam(value = "subCategory", required = false) String subCategory,
                               @RequestParam(value = "page" ,defaultValue = "0", required = false) int pageIndex,
                               @RequestParam(value = "size" ,defaultValue = "10", required = false) int pageSize){

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<QuestionBriefBo> result = questionService.userBanks(userId, action, search, type, mainCategory, subCategory, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        }catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        }
    }
}
