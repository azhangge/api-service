package com.huajie.educomponent.pubrefer.entity;

import com.huajie.appbase.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fangxing on 17-7-5.
 */
@Data
@Entity
@Table(name = "course_category")
public class CourseCategoryEntity extends BaseEntity {

    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "app_icon")
    private String appIcon;
    @Column(name = "descriptions")
    private String descriptions;
    @Column(name = "parent_id")
    private String parentId;
}
