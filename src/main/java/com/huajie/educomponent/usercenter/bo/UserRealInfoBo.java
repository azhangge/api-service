package com.huajie.educomponent.usercenter.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Created by zgz on 2017/9/28.
 */
@Data
public class UserRealInfoBo {

    private String userRealInfoId;
    private String realName;
    private String idNo;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date idEndTime;//证件过期时间
    private String idCardFrontImgId;  //身份证正面照
    private String idCardBackImgId;  //身份证反面照
    private String idCardFrontFilePath;  //身份证正面照路径
    private String idCardBackFilePath;  //身份证反面照路径
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date authenticationTime;//认证时间
    private Integer approveStatus;//是否认证通过 0申请中 1通过 2不通过
}
