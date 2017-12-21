package com.huajie.orgcenter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fangxing on 17-7-6.
 */
@Entity
@Table(name = "org_group")
public class GroupEntity extends RbacBaseEntity {
    @Column(name = "org_id")
    private String orgId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
