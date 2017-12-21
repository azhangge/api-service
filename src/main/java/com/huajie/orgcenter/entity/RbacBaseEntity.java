package com.huajie.orgcenter.entity;

import com.huajie.appbase.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by fangxing on 17-7-6.
 */
@MappedSuperclass
public abstract class RbacBaseEntity extends BaseEntity {
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
