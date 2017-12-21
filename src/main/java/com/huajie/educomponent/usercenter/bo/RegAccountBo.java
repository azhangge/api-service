package com.huajie.educomponent.usercenter.bo;

import lombok.Data;

/**
 * Created by fangxing on 17-7-3.
 */
@Data
public class RegAccountBo {
    private String username;
    private String phoneNo;
    private String password;
    private int type; //3 注册  4 找回密码
    private String code;
    private String sysId;
    private int device;
}
