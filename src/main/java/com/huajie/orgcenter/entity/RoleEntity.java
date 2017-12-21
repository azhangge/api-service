package com.huajie.orgcenter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fangxing on 17-7-6.
 */
@Entity
@Table(name = "org_role")
public class RoleEntity extends RbacBaseEntity {
    @Column(name = "org_id")
    private String orgId;
    @Column(name = "chief_role_id")
    private String chiefRoleId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getChiefRoleId() {
        return chiefRoleId;
    }

    public void setChiefRoleId(String chiefRoleId) {
        this.chiefRoleId = chiefRoleId;
    }
}
