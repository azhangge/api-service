package com.huajie.educomponent.usercenter.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fangxing on 17-7-3.
 */
@Data
@Entity
@Table(name = "pub_sys_info")
public class SysInfoEntity extends IdEntity {

    @Column(name = "sys_name")
    private String sysName;
    @Column(name = "sys_host")
    private String sysHost;
    @Column(name = "sys_port")
    private int sysPort;

}
