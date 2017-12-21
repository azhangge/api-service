package com.huajie.educomponent.usercenter.bo;

import lombok.Data;
import java.util.List;

/**
 * Created by 10070 on 2017/8/2.
 */
@Data
public class AccountReBo {
    private String userId;
    private String nickName;
    private String avatarUrl;
    private String token;
    private String targetSysId;
    private String phoneNo;
    private List<SysInfoBo> userSysList;
}
