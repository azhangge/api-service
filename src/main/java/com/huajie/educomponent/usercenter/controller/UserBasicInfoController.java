package com.huajie.educomponent.usercenter.controller;

import com.huajie.appbase.BaseRetBo;
import com.huajie.appbase.BaseRetMessage;
import com.huajie.appbase.BaseRetType;
import com.huajie.appbase.BusinessException;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.usercenter.bo.UserIconBo;
import com.huajie.educomponent.usercenter.bo.UserBasicInfoUpdateBo;
import com.huajie.educomponent.usercenter.bo.UserBasicInfoBo;
import com.huajie.educomponent.usercenter.bo.UserRoleBo;
import com.huajie.educomponent.usercenter.service.UserBasicInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 10070 on 2017/8/2.
 */
@RestController
@RequestMapping("/account/user_info")
public class UserBasicInfoController {

    @Autowired
    private UserBasicInfoService userBasicInfoService;

    @ApiOperation("修改用户信息")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public BaseRetBo modifyUserInfo(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                    @RequestBody UserBasicInfoUpdateBo userBasicInfoUpdateBo) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            userBasicInfoService.modifyUserBasicInfo(userId, userBasicInfoUpdateBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseRetBo getAccount(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            UserBasicInfoBo userBasicInfoBo = userBasicInfoService.getUserBasicInfo(userId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SET_USER_ICON.getValue(), userBasicInfoBo);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("设置用户图片")
    @RequestMapping(value = "/icon", method = RequestMethod.POST)
    public BaseRetBo setUserIcon(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                 @RequestBody UserIconBo userIconBo) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            userBasicInfoService.setUserBasicIcon(userId, userIconBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SET_USER_ICON.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("获取用户角色信息")
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public BaseRetBo getUserRole(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            UserRoleBo result = userBasicInfoService.getUserRole(userId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
	
	@ApiOperation("修改个人基本信息")
    @RequestMapping(value = "/updPersonalInfo", method = RequestMethod.POST)
    public BaseRetBo modifyPersonInfo(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                      @ModelAttribute(value = "userBasicInfoBo") UserBasicInfoBo userBasicInfoBo, BindingResult result) {
        BaseRetBo retBo = new BaseRetBo();
        UserBasicInfoUpdateBo userBasicInfoUpdateBo = new UserBasicInfoUpdateBo();

        try {
            BeanUtils.copyProperties(userBasicInfoBo,userBasicInfoUpdateBo);
            userBasicInfoService.modifyUserBasicInfo(userId,userBasicInfoUpdateBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
}
