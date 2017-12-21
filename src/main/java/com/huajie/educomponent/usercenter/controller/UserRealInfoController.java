package com.huajie.educomponent.usercenter.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.service.FileStorageService;
import com.huajie.educomponent.usercenter.bo.UserRealApproveBo;
import com.huajie.educomponent.usercenter.bo.UserRealInfoBo;
import com.huajie.educomponent.usercenter.bo.UserRealInfoCreateBo;
import com.huajie.educomponent.usercenter.service.UserRealInfoService;
import com.huajie.utils.PathUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zgz on 2017/9/28.
 */
@RestController
@RequestMapping("/account/realinfo")
public class UserRealInfoController {

    @Autowired
    private UserRealInfoService userRealInfoService;

    @ApiOperation("申请实名认证,不允许修改")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseRetBo modifyUserInfo(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                    @RequestBody UserRealInfoCreateBo userRealInfoCreateBo) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            UserRealInfoBo result = userRealInfoService.requestAuth(userId, userRealInfoCreateBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("获取用户实名信息")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseRetBo modifyUserInfo(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            UserRealInfoBo result = userRealInfoService.getUserRealInfo(userId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("管理员用户实名列表查询")
    @RequestMapping(value = "/manage/list", method = RequestMethod.POST)
    public BaseRetBo userRealList(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                    @RequestParam(value = "realName", required = false) String realName,
                                    @RequestParam(value = "approveStatus", required = false) Integer approveStatus,
                                    @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<UserRealInfoBo> result = userRealInfoService.userRealSearch(realName, approveStatus, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("用户实名审批")
    @RequestMapping(value = "/manage/approve", method = RequestMethod.POST)
    public BaseRetBo userRealApprove(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                     @RequestBody UserRealApproveBo userRealApproveBo) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            userRealInfoService.userRealApprove(userRealApproveBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("Web端获取用户实名信息")
    @RequestMapping(value = "/realinfo", method = RequestMethod.GET)
    public ModelAndView getUserExamNotices(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                           HttpServletRequest request) {

        UserRealInfoBo userRealInfoBo = userRealInfoService.getUserRealInfo(userId);

        ModelAndView view = new ModelAndView("page/userrealinfo");
        view.addObject("userRealInfoBo", userRealInfoBo);
        view.addObject("userRealInfoCreateBo", new UserRealInfoCreateBo());

        return view;
    }

    @ApiOperation("Web端申请实名认证")
    @RequestMapping(value = "/realNameAuth", method = RequestMethod.POST)
    public BaseRetBo realNameAuth(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                  @ModelAttribute(value="userRealInfoCreateBo") UserRealInfoCreateBo userRealInfoCreateBo, BindingResult result) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            UserRealInfoBo userRealInfoBo = userRealInfoService.requestAuth(userId, userRealInfoCreateBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), userRealInfoBo);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }


    @ApiOperation("上传身份证照片")
    @RequestMapping(value = "/filePath", method = RequestMethod.GET)
    public String getFilePath(@ModelAttribute(value="fileStorageBo")FileStorageBo fileStorageBo) {
        return PathUtils.getFilePath(fileStorageBo);
    }
}
