package com.huajie.educomponent.usercenter.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fangxing on 17-7-5.
 */
@Data
@Entity
@Table(name = "pub_check_code")
public class CheckCodeEntity extends IdEntity {

    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "code")
    private String code;
    @Column(name = "sys_id")
    private String sysId;
    @Column(name = "expiry_date")
    private Date expiryDate;

}
