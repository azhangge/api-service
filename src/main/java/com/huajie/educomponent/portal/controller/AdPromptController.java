package com.huajie.educomponent.portal.controller;

import com.huajie.appbase.*;
import com.huajie.educomponent.portal.bo.AdPromptBo;
import com.huajie.educomponent.portal.bo.ResourceBo;
import com.huajie.educomponent.portal.service.AdPromptService;
import com.huajie.educomponent.portal.service.ResourceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by zgz on 2017/8/24.
 */
@RestController
@RequestMapping("/ad")
public class AdPromptController {

    private static  Integer A;



    @Autowired
    private AdPromptService adPromptService;

    @Autowired
    private ResourceService resourceService;

    @ApiOperation("新建、更新轮播图")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseRetBo save(@RequestBody AdPromptBo adPromptBo){

        BaseRetBo retBo = new BaseRetBo();
        try {
            AdPromptBo result = adPromptService.save(adPromptBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        }catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("查询，不分页")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseRetBo get(@RequestParam(value = "isValid", required = false)Boolean isValid){
        BaseRetBo retBo = new BaseRetBo();
        try {
            List<AdPromptBo> result = adPromptService.get(isValid);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("删除轮播图")
    @RequestMapping(value = "/{adPromptId}", method = RequestMethod.DELETE)
    public BaseRetBo delete(@PathVariable(value = "adPromptId")String adPromptId){
        BaseRetBo retBo = new BaseRetBo();
        try {
            adPromptService.delete(adPromptId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("获取课程资源列表")
    @RequestMapping(value = "/getCourses", method = RequestMethod.GET)
    public  BaseRetBo getCourses(@RequestParam(value = "search", required = false) String search,
                                  @RequestParam(value = "isPublic", required = false) Boolean isPublic,
                                  @RequestParam(value = "isOnShelves", required = false) Boolean isOnShelves,
                                  @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<ResourceBo> resourceBoPageResult = resourceService.getCourses(search, isPublic, isOnShelves, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), resourceBoPageResult);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("获取通知资源列表")
    @RequestMapping(value = "/getExamNotices", method = RequestMethod.GET)
    public  BaseRetBo getExamNotices(@RequestParam(value = "search", required = false) String search,
                                 @RequestParam(value = "isPublic", required = false) Boolean isPublic,
                                 @RequestParam(value = "isPublish", required = false) Boolean isPublish,
                                 @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<ResourceBo> resourceBoPageResult = resourceService.getExamNotices(search, isPublic, isPublish, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), resourceBoPageResult);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("获取广告列表")
    @RequestMapping(value = "/getAdPromps", method = RequestMethod.GET)
    public  BaseRetBo getAdPromps(@RequestParam(value = "search", required = false) String search,
                                     @RequestParam(value = "resourceType", required = false) Integer resourceType,
                                     @RequestParam(value = "pageIndex", defaultValue = "0") int pageIndex,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<AdPromptBo> adPromptBoPageResult = adPromptService.getAdPrompts(resourceType, search, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), adPromptBoPageResult);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("获取轮播图详情")
    @RequestMapping(value = "/getAdPromp/{adPromptId}", method = RequestMethod.GET)
    public  BaseRetBo getAdPromp(@PathVariable(value = "adPromptId") String adPromptId) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            AdPromptBo adPromptBo = adPromptService.getOne(adPromptId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), adPromptBo);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
}
