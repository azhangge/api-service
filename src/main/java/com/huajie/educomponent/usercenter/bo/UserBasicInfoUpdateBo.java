package com.huajie.educomponent.usercenter.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Created by 10070 on 2017/8/5.
 */
@Data
public class UserBasicInfoUpdateBo {

    private String userId;
    private String nickName;
    private Integer age; //0 保密  1 男  2女
    private Integer gender;
    private String qq;
    private String email;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date birthday;
    private String phoneNo;
    private String cityId;
    private String provinceId;
    private String companyName;
    private String position;

}
