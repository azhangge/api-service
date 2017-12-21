package com.huajie.appbase;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by fangxing on 17-7-5.
 */
@Data
@MappedSuperclass
public abstract class BaseEntity extends IdEntity {
    @Column(name = "creator_id")
    private String creatorId;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "modifier_id")
    private String modifierId;
    @Column(name = "modify_date")
    private Date modifyDate;
    @Column(name = "active")
    private int active;


    public void updateModifyInfo(String modifierId) {
        this.modifierId = modifierId;
        this.modifyDate = new Date();
    }

    public void updateCreateInfo(String creatorId) {
        this.creatorId = creatorId;
        this.createDate = new Date();
    }
}
