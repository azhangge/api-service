package com.huajie.educomponent.usercenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;

/**
 * Created by fangxing on 17-7-3.
 */

@Controller
@RequestMapping("")
public class HomeController {

    @RequestMapping("usercenterhome")
    public ModelAndView tempalte() {
        return new ModelAndView("page/usercentertemplate");
    }

    @RequestMapping("user/security")
    public ModelAndView usersecurity() {
        return new ModelAndView("page/usersecurity");
    }

}
