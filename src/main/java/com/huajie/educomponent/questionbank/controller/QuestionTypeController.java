package com.huajie.educomponent.questionbank.controller;

import com.huajie.appbase.BaseRetBo;
import com.huajie.appbase.BaseRetMessage;
import com.huajie.appbase.BaseRetType;
import com.huajie.educomponent.questionbank.bo.QuestionTypeBo;
import com.huajie.educomponent.questionbank.service.QuestionTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by zgz on 2017/7/21.
 */
@RestController
@RequestMapping("/question_type")
public class QuestionTypeController {

    @Autowired
    private QuestionTypeService questionTypeService;

    @ApiOperation("新建、更新题库类型")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseRetBo create(@CookieValue(value = "userId")String userId,
                            @RequestBody QuestionTypeBo questionTypeBo){

        BaseRetBo retBo = new BaseRetBo();
        try {
            QuestionTypeBo result = questionTypeService.save(userId, questionTypeBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        }catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        }
    }

    @ApiOperation("查询")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseRetBo getAll() {
        BaseRetBo retBo = new BaseRetBo();
        try {
            List<QuestionTypeBo> result = questionTypeService.get();
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        }catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        }
    }

    @ApiOperation("删除")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public BaseRetBo delete(@RequestParam(value = "questionTypeId")String questionTypeId){
        BaseRetBo retBo = new BaseRetBo();
        try {
            questionTypeService.delete(questionTypeId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        }catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        }
    }

}
