package com.huajie.educomponent.suggestion.controller;

import com.huajie.appbase.*;
import com.huajie.educomponent.suggestion.bo.SuggestionBo;
import com.huajie.educomponent.suggestion.service.SuggestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zgz on 2017/9/6.
 */
@RestController
@RequestMapping("/suggestion")
public class SuggestionController {

    @Autowired
    private SuggestionService suggestionService;

    @ApiOperation("创建")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseRetBo save(@CookieValue(value = "userId", required = false) String userId,
                          @RequestBody SuggestionBo suggestionBo){

        BaseRetBo retBo = new BaseRetBo();
        try {
            String suggestionId = suggestionService.create(userId, suggestionBo);
            IdRetBo result = new IdRetBo(suggestionId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        }
    }

    @ApiOperation("查询")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseRetBo get(@CookieValue(value = "userId") String userId,
                         @RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(value = "type", required = false) Integer type,
                         @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize){

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<SuggestionBo> result = suggestionService.get(userId, keyword, type, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        }
    }
}
