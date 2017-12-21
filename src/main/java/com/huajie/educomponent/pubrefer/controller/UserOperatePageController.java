package com.huajie.educomponent.pubrefer.controller;

import com.huajie.appbase.PageResult;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.pubrefer.constants.UserOperateType;
import com.huajie.educomponent.pubrefer.service.UserOperateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class UserOperatePageController {

    @Autowired
    private UserOperateService userOperateService;

    @ApiOperation("查询")
    @RequestMapping(value = "/usercenter/operate", method = RequestMethod.GET)
    public ModelAndView getUserCourseEnd(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                         @RequestParam(value = "type") Integer type,
                                         @RequestParam(value = "timeOut", required = false, defaultValue = "false") Boolean timeOut,
                                         @RequestParam(value = "pageIndex", defaultValue = "0") int page,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int size) {

        PageResult<Object> result = userOperateService.getUserOperate(userId, type, timeOut, page, size);
        ModelAndView view = new ModelAndView();
        if (type == UserOperateType.JOIN_IN.getValue()){
            view.setViewName("page/userlearningcourse");
        } else if (type == UserOperateType.BUY.getValue()){
            view.setViewName("page/userbuycourse");
        } else if (type == UserOperateType.FAVORITE_COURSE.getValue()){
            view.setViewName("page/userfavoritecourse");
        } else if (type == UserOperateType.FAVORITE_QUESTION_SET.getValue()){
            view.setViewName("page/userfavoritequestionset");
        }else {
            //错误页面
        }
        view.addObject("result", result);
        return view;

    }
}
