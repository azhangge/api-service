package com.huajie.educomponent.usercenter.bo;

import lombok.Data;

/**
 * Created by fangxing on 17-7-5.
 */
@Data
public class LoginBo {
    private String userName;
    private String phoneNo;
    private String password;
    private String gesture;
    private String sysId;
    private String code;//登录校验码
    private int type;//1 字符密码  2 手势
    private int isSwap;//0 不切换子系统 1 切换
    private int device;//0 web, 1 mobil

}
