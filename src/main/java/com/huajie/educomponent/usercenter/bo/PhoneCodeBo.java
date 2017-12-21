package com.huajie.educomponent.usercenter.bo;

import lombok.Data;
import java.util.Date;

/**
 * Created by 10070 on 2017/8/5.
 */
@Data
public class PhoneCodeBo {

    private String phoneNo;
    private String code;
    private String sysId;
    private Date expiryDate;
}
