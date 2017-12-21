package com.huajie.orgcenter.entity;

import com.huajie.appbase.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fangxing on 17-7-5.
 */
@Entity
@Table(name = "org_org")
public class OrgEntity extends RbacBaseEntity{
    @Column(name = "parent_org_id")
    private String parentOrgId;

    public String getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(String parentOrgId) {
        this.parentOrgId = parentOrgId;
    }
}
