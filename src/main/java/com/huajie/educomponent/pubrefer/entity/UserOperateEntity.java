package com.huajie.educomponent.pubrefer.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fangxing on 17-7-11.
 */
@Data
@Entity
@Table(name = "user_operate")
public class UserOperateEntity extends IdEntity {

    @Column(name = "object_id")
    private String objectId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "type")
    private Integer type;
    @Column(name = "time")
    private Date time;
}
