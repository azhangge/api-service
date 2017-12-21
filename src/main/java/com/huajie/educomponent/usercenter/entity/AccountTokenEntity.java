package com.huajie.educomponent.usercenter.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fangxing on 17-7-4.
 */
@Data
@Entity
@Table(name = "account_token")
public class AccountTokenEntity extends IdEntity {

    @Column(name = "account_id")
    private String accountId;
    @Column(name = "last_access_time")
    private Date lastAccessTime;
    @Column(name = "device")
    private Integer device;
}
