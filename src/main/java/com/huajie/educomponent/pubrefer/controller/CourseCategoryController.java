package com.huajie.educomponent.pubrefer.controller;

import com.huajie.appbase.BaseRetBo;
import com.huajie.appbase.BaseRetMessage;
import com.huajie.appbase.BaseRetType;
import com.huajie.appbase.BusinessException;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.pubrefer.bo.CourseCategoryModBo;
import com.huajie.educomponent.pubrefer.bo.CourseCategoryNodeBo;
import com.huajie.educomponent.pubrefer.bo.CourseCategoryTreeNodeBo;
import com.huajie.educomponent.pubrefer.bo.CourseCategoryHotBo;
import com.huajie.educomponent.pubrefer.service.CourseCategoryService;
import com.huajie.educomponent.usercenter.service.UserBasicInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by fangxing on 17-7-24.
 */
@RestController
@RequestMapping("/course_category")
public class CourseCategoryController {

    @Autowired
    private CourseCategoryService courseCategoryService;

    @Autowired
    private UserBasicInfoService userBasicInfoService;


    @ApiOperation("添加、更新课程分类")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseRetBo save(@CookieValue(value="userId", defaultValue = EnvConstants.userId)String userId,
                          @RequestBody CourseCategoryNodeBo nodeBo) {

        BaseRetBo retBo = new BaseRetBo();
        if (userId == null) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), "请重新登陆！");
        }
        try {
            courseCategoryService.save(userId, nodeBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }


    @ApiOperation("添加、更新课程分类")
    @RequestMapping(value = "/savebatch", method = RequestMethod.POST)
    public BaseRetBo save(@CookieValue(value="userId", defaultValue = EnvConstants.userId)String userId,
                          @RequestBody CourseCategoryModBo modBos) {

        BaseRetBo retBo = new BaseRetBo();
        if (userId == null) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), "请重新登陆！");
        }

        try {
            if (modBos.getChanged() != null && modBos.getChanged().size() > 0) {
                courseCategoryService.saveBatch(userId, modBos.getChanged());
            }

            if (modBos.getDeleted() != null && modBos.getDeleted().size() > 0) {
                courseCategoryService.deleteBatch(userId, modBos.getDeleted());
            }

            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("删除课程分类")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public BaseRetBo delete(@CookieValue(value="userId", defaultValue = EnvConstants.userId)String userId,
                            @RequestParam(value = "categoryId")String categoryId) {

        BaseRetBo retBo = new BaseRetBo();
        if (userId == null) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), "请重新登陆！");
        }

        try {
            courseCategoryService.delete(userId, categoryId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("获取课程分类")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public BaseRetBo getTree(){

        BaseRetBo retBo = new BaseRetBo();
        try {
            List<CourseCategoryTreeNodeBo> treeNodeBos = courseCategoryService.getCourseCategoryTree();
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), treeNodeBos);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("最热课程的类别")
    @RequestMapping(value = "/hot_category", method = RequestMethod.GET)
    public BaseRetBo getHotCategory(@RequestParam(value = "mainCount", required = false, defaultValue = "4") Integer mainCount,
                                    @RequestParam(value = "subCount", required = false, defaultValue = "4") Integer subCount){

        BaseRetBo retBo = new BaseRetBo();
        try {
            CourseCategoryHotBo result = courseCategoryService.getHotCategoryList(mainCount, subCount);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e){
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

}
