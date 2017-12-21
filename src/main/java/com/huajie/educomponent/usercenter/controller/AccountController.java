package com.huajie.educomponent.usercenter.controller;

import com.github.pagehelper.util.StringUtil;
import com.huajie.appbase.BaseRetBo;
import com.huajie.appbase.BaseRetMessage;
import com.huajie.appbase.BaseRetType;
import com.huajie.appbase.BusinessException;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.usercenter.bo.*;
import com.huajie.educomponent.usercenter.constans.AccountOpType;
import com.huajie.educomponent.usercenter.constans.PasswordType;
import com.huajie.educomponent.usercenter.service.AccountService;
import com.huajie.educomponent.usercenter.service.CheckCodeService;
import com.huajie.educomponent.usercenter.service.UserBasicInfoService;
import com.huajie.utils.CookieUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by fangxing on 17-7-3.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CheckCodeService checkCodeService;

    @Autowired
    private UserBasicInfoService userBasicInfoService;

    @ApiOperation("登录")
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseRetBo login(@RequestBody LoginBo loginBo, HttpServletRequest request, HttpServletResponse response) {

        BaseRetBo retBo = new BaseRetBo();
        if (false == accountService.verifyLoginAccount(loginBo)) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.ARGUMENT_BLANK.getValue());
        }
        try {
            AccountReBo accountReBo = accountService.login(loginBo);
            if (accountReBo.getTargetSysId() != null) {
                CookieUtils.writeCookie(accountReBo, request, response);
                return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.LOGIN_SUCCESS.getValue(), accountReBo);
            } else {
                return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.NO_RIGHT.getValue());
            }
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("登录")
    @ResponseBody
    @RequestMapping(value = "/loginportal", method = RequestMethod.POST)
    public BaseRetBo loginPortal(@RequestBody LoginBo loginBo, HttpServletRequest request, HttpServletResponse response) {

        BaseRetBo retBo = new BaseRetBo();
        if (false == accountService.verifyLoginAccountForWeb(loginBo)) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.ARGUMENT_BLANK.getValue());
        }
        try {
            AccountReBo accountReBo = accountService.loginByAccount(loginBo);
            if (accountReBo.getTargetSysId() != null) {
                CookieUtils.writeCookie(accountReBo, request, response);
                HttpSession session = request.getSession();
                session.setAttribute("nickName", accountReBo.getNickName());
                session.setAttribute("isLogin", true);
                session.setAttribute("userId",accountReBo.getUserId());
                return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.LOGIN_SUCCESS.getValue(), accountReBo);
            } else {
                return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.NO_RIGHT.getValue());
            }
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @RequestMapping(value = "shirologin", method = RequestMethod.GET)
    public String shiroLogin(@RequestBody LoginBo loginBo) {
        return "success";
    }


    @ApiOperation("退出")
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public BaseRetBo logout(HttpServletRequest request, HttpServletResponse response) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            String token = request.getHeader("Authorization");
            Boolean result = accountService.logout(token);
            if (result == true) {
                CookieUtils.cleanCookie(request, response);
                return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.LOGOUT.getValue());
            }
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
        return null;
    }

    @ApiOperation("退出")
    @RequestMapping(value = "/logoutportal", method = RequestMethod.GET)
    public String logoutportal(HttpServletRequest request, HttpServletResponse response) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            String token = request.getHeader("Authorization");
            Boolean result = accountService.logout(token);
            if (result == true) {
                CookieUtils.cleanCookie(request, response);
                HttpSession session = request.getSession();
                session.invalidate();
            }

        } catch (BusinessException e) {
            //return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            //return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        } finally {
            return "redirect:/";
        }

    }

    @ApiOperation("注册")
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseRetBo procRegister(@RequestBody RegAccountBo regAccountBo,
                                  HttpServletRequest request, HttpServletResponse response) {
        BaseRetBo retBo = new BaseRetBo();
        if (false == accountService.verifyRegAccount(regAccountBo)) {
            retBo.setRetCode(BaseRetType.FAILED.getValue());
            retBo.setMessage(BaseRetMessage.ARGUMENT_BLANK.getValue());
            return retBo;
        }
        try {
            AccountReBo accountReBo = new AccountReBo();
            if (regAccountBo.getType() == AccountOpType.REGISTER.getValue()) {
                accountReBo = accountService.register(regAccountBo);
            }
            if (regAccountBo.getType() == AccountOpType.FIND_PASSWORD.getValue()) {
                accountReBo = accountService.remind(regAccountBo);
            }
            CookieUtils.writeCookie(accountReBo, request, response);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), accountReBo);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("修改密码")
    @ResponseBody
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public BaseRetBo changePassword(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                    @RequestBody PasswordChangeBo passwordChangeBo,HttpServletRequest request, HttpServletResponse response) {
        BaseRetBo retBo = new BaseRetBo();
        if (passwordChangeBo.getPwdType() == PasswordType.GESTURE.getValue()) {
            retBo.setMessage("不支持修改手势密码");
            retBo.setRetCode(BaseRetType.FAILED.getValue());
            return retBo;
        }
        try {
            AccountReBo accountReBo = accountService.changePassword(userId, passwordChangeBo);
            CookieUtils.writeCookie(accountReBo, request, response);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), accountReBo);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }


    @RequestMapping(value = "modifypwd")
    public ModelAndView changePwdForm() {
        return new ModelAndView("page/usersecurity");
    }

    @ApiOperation("修改密码")
    @ResponseBody
    @RequestMapping(value = "/modifypwd", method = RequestMethod.POST)
    public BaseRetBo changePasswordForWeb(PasswordChangeBo passwordChangeBo,
                                    HttpServletRequest request, HttpServletResponse response) {
        BaseRetBo retBo = new BaseRetBo();
        String userId=(String)request.getSession().getAttribute("userId");
        if(StringUtil.isEmpty(userId)){
            retBo.setMessage("用户登陆异常");
            retBo.setRetCode(BaseRetType.FAILED.getValue());
            return retBo;
        }

        if (passwordChangeBo.getPwdType() == PasswordType.GESTURE.getValue()) {
            retBo.setMessage("不支持修改手势密码");
            retBo.setRetCode(BaseRetType.FAILED.getValue());
            return retBo;
        }
        try {
            AccountReBo accountReBo = accountService.changePassword(userId, passwordChangeBo);
            CookieUtils.writeCookie(accountReBo, request, response);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), accountReBo);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
    @ApiOperation("获取手机验证码")
    @ResponseBody
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public BaseRetBo changePassword(@RequestParam(value = "phoneNo") String phoneNo,
                                    @RequestParam(value = "sysId") String sysId,
                                    @RequestParam(value = "type", required = false) Integer type,
                                    @RequestParam(value = "device", required = false) Integer device) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            checkCodeService.sendCode(phoneNo, sysId, type, device);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("设置、修改手势密码")
    @ResponseBody
    @RequestMapping(value = "/gesture", method = RequestMethod.POST)
    public BaseRetBo setGesture(@RequestBody GesturePasswordBo bo) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            accountService.setGesture(bo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
	
	@ApiOperation("用户获取个人基本信息")
    @RequestMapping(value = "/basicinfo/personalinfo", method = RequestMethod.GET)
    public ModelAndView getUserExamNotices(@CookieValue(value = "token") String token,HttpServletRequest request) {

        AccountBo accountBo = accountService.getAccount(token);
        UserBasicInfoBo userBasicInfoBo = userBasicInfoService.getUserBasicInfo(accountBo.getUserId());

        ModelAndView view = new ModelAndView("page/userbasicinfo");
        view.addObject("accountBo", accountBo);
        view.addObject("userBasicInfoBo", userBasicInfoBo);

        return view;
    }

    @ApiOperation("pc端注册")
    @ResponseBody
    @RequestMapping(value = "/registerportal", method = RequestMethod.POST)
    public BaseRetBo registerportal(@RequestBody RegAccountBo regAccountBo,
                                    HttpServletRequest request, HttpServletResponse response) {
        BaseRetBo retBo = new BaseRetBo();
        if (false == accountService.verifyRegAccount(regAccountBo)) {
            retBo.setRetCode(BaseRetType.FAILED.getValue());
            retBo.setMessage(BaseRetMessage.ARGUMENT_BLANK.getValue());
            return retBo;
        }


        try {
            //注册
            regAccountBo.setType(AccountOpType.REGISTER.getValue());
            AccountReBo registerAccountReBo = accountService.registerByAccount(regAccountBo);

            //登录
            LoginBo loginBo = new LoginBo();
            loginBo.setUserName(regAccountBo.getUsername());
            loginBo.setPassword(regAccountBo.getPassword());
            loginBo.setSysId(regAccountBo.getSysId());
            loginBo.setType(PasswordType.STRING.getValue());
            AccountReBo loginAccountReBo = accountService.loginByAccount(loginBo);
            CookieUtils.writeCookie(loginAccountReBo, request, response);
            HttpSession session = request.getSession();
            session.setAttribute("nickName", loginAccountReBo.getNickName());
            session.setAttribute("isLogin", true);
            session.setAttribute("userId",loginAccountReBo.getUserId());
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.LOGIN_SUCCESS.getValue(), loginAccountReBo);

        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        }
    }
}
