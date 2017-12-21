package com.huajie.educomponent.usercenter.bo;

import lombok.Data;

/**
 * Created by zgz on 2017/9/29.
 */
@Data
public class UserRealApproveBo {

    private String userRealInfoId;
    private Integer approveStatus;//1实名通过  2实名不通过
}
