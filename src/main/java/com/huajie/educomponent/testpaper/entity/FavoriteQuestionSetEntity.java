package com.huajie.educomponent.testpaper.entity;

import com.huajie.appbase.BaseEntity;
import io.swagger.annotations.Api;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 10070 on 2017/7/24.
 */
@Api("试卷、题集")
@Data
@Entity
@Table(name = "user_operate")
public class FavoriteQuestionSetEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "object_id",insertable=false,updatable=false)
    private String objectId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "type")
    private Integer type;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "object_id",nullable = false)
    private NewQuestionSetEntity newQuestionSetEntity;
}
