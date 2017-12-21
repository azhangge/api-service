package com.huajie.educomponent.usercenter.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zgz on 2017/9/29.
 */
@Data
@Entity
@Table(name = "account_role")
public class AccountRoleEntity extends IdEntity{

    @Column(name = "user_id")
    private String userId;
    @Column(name = "role_type")
    private Integer roleType;//0 普通，1 讲师，2 管理员
    @Column(name = "create_time")
    private Date createTime;//成为该角色的时间
}
