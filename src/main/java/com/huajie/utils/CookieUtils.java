package com.huajie.utils;


import com.huajie.educomponent.usercenter.bo.AccountReBo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 10070 on 2017/7/31.
 */
public class CookieUtils {

    public static void writeCookie(AccountReBo accountReBo, HttpServletRequest request, HttpServletResponse response){
        Cookie userCookie = new Cookie("userId", accountReBo.getUserId());
        Cookie tokenCookie = new Cookie("token", accountReBo.getToken());
        userCookie.setPath("/");
        tokenCookie.setPath("/");
        userCookie.setMaxAge(-1);
        tokenCookie.setMaxAge(-1);
        response.addCookie(userCookie);
        response.addCookie(tokenCookie);
    }

    public static void cleanCookie(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return;
        }
        for (Cookie cookie:cookies){
            if ("userId".equals(cookie.getName())){
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                return;
            }
        }
    }

    public static String getUserId(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        for (Cookie cookie:cookies){
            if ("userId".equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

    public static String getToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            return null;
        }
        for (Cookie cookie:cookies){
            if ("token".equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
}
