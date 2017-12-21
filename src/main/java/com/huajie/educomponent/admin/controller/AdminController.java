package com.huajie.educomponent.admin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {
    @RequestMapping("/admin")
    String index(HttpServletRequest request) {
        return "redirect:static/admin/index.html";
    }
}
