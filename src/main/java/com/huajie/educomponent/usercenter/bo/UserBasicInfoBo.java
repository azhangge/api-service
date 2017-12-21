package com.huajie.educomponent.usercenter.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by fangxing on 17-7-5.
 */
@Data
public class UserBasicInfoBo {

    private String userId;
    private String phoneNo;
    private String nickName;
    private Integer age;
    private Integer gender;//0 保密  1 男  2女
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date birthday;
    private String idNo;
    private String cityId;
    private String provinceId;
    private String companyName;
    private String position;
    private String qq;
    private String email;
    private String icon;
    private String filePath;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createDate;

}
