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
@Table(name = "account_log")
public class AccountLogEntity extends IdEntity {

    @Column(name = "account_id")
    private String accountId;
    @Column(name = "op_type")
    private int opType;
    @Column(name = "date")
    private Date date;

}
