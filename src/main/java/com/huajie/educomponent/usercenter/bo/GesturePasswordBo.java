package com.huajie.educomponent.usercenter.bo;

import lombok.Data;

/**
 * Created by zgz on 2017/8/29.
 */
@Data
public class GesturePasswordBo {

    private String gesture;
    private String sysId;
    private String phoneNo;
    private Integer type;//1 设置  2 查询
}
