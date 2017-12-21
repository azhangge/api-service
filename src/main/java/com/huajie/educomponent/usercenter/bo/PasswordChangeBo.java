package com.huajie.educomponent.usercenter.bo;

import lombok.Data;

/**
 * Created by fangxing on 17-7-6.
 */
@Data
public class PasswordChangeBo {

    private String oldPassword; //修改手势密码的时候可以不要
    private String newPassword;
    private String sysId;
    private int pwdType;//密码类型 1 字符  2 手势
}
