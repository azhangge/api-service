package com.huajie.educomponent.usercenter.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zgz on 2017/9/28.
 */
@Data
@Entity
@Table(name = "account_user_real_info")
public class UserRealInfoEntity extends IdEntity{

    @Column(name = "real_name")
    private String realName;
    @Column(name = "id_no")
    private String idNo;
    @Column(name = "id_end_time")
    private Date idEndTime;//证件过期时间
    @Column(name = "id_card_front_img_id")
    private String idCardFrontImgId;  //身份证正面照
    @Column(name = "id_card_back_img_id")
    private String idCardBackImgId;  //身份证反面照
    @Column(name = "authentication_time")
    private Date AuthenticationTime;//认证时间
    @Column(name = "approve_status")
    private Integer approveStatus;//是否认证通过 0申请中 1通过 2不通过
}
