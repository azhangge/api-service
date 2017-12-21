package com.huajie.educomponent.usercenter.bo;

import lombok.Data;
import java.util.Date;

/**
 * Created by 10070 on 2017/7/31.
 */
@Data
public class AccountBo {
    private String username;
    private String password;
    private String gesture;
    private String email;
    private String phoneNo;
    private String sysId;
    private String userId;
    private String sessionId;
    private Date createTime;
}
