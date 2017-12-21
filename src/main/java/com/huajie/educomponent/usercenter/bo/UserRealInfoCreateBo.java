package com.huajie.educomponent.usercenter.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by zgz on 2017/9/28.
 */
@Data
public class UserRealInfoCreateBo {

    private String realName;
    private String idNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date idEndTime;//证件过期时间
    private String idCardFrontImgId;  //身份证正面照
    private String idCardBackImgId;  //身份证反面照
}
