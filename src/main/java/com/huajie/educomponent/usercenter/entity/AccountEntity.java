package com.huajie.educomponent.usercenter.entity;

import com.huajie.appbase.IdEntity;
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
@Table(name="account_account")
public class AccountEntity extends IdEntity {

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "gesture")
    private String gesture;
    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "sys_id")
    private String sysId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "session_id")
    private String sessionId;
    @Column(name = "create_time")
    private Date createTime;
}
