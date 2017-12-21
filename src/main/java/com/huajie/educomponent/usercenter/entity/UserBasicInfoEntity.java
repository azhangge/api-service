package com.huajie.educomponent.usercenter.entity;

import com.huajie.appbase.BaseEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fangxing on 17-7-3.
 */
@Data
@Entity
@Table(name = "account_user_basic_info")
public class UserBasicInfoEntity extends BaseEntity {

    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "icon")
    private String icon;
    @Column(name = "gender")
    private Integer gender;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "city_id")
    private String cityId;
    @Column(name = "province_id")
    private String provinceId;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "position") //职务
    private String position;
    @Column(name = "qq")
    private String qq;
    @Column(name = "user_real_info_id")
    private String userRealInfoId;//实名id
}
