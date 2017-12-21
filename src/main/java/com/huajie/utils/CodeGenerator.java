package com.huajie.utils;

import java.util.UUID;

/**
 * Created by fangxing on 17-7-4.
 */
public class CodeGenerator {
    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getCellphoneCheckCode(){
        //Todo 使用第三方库生成手机验证码
        return null;
    }

    public static String getLoginCheckCode(){
        //Todo 使用第三方库生成登录验证码
        return null;
    }
}
