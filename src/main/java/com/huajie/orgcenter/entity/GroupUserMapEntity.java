package com.huajie.orgcenter.entity;

import com.huajie.appbase.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fangxing on 17-7-6.
 */
@Entity
@Table(name = "org_group_user_map")
public class GroupUserMapEntity extends BaseEntity {
    @Column(name = "group_id")
    private String groupId;
    @Column(name = "user_id")
    private String userId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
