package com.huajie.educomponent.portal.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by zgz on 2017/8/24.
 */
@Data
@Entity
@Table(name = "sys_ad_prompt")
public class AdPromptEntity extends IdEntity {

    @Column(name = "name")
    private String name;//内容名称
    @Column(name = "ad_index")
    private Integer adIndex;//展示顺序
    @Column(name = "descriptions")
    private String descriptions;//内容说明
    @Column(name = "resource_id")
    private String resourceId;//绑定的资源ID
    @Column(name = "resource_type")
    private int resourceType;//绑定的资源类型:1、课程；2、考试通知、3活动通知
    @Column(name = "web_ad_img_Id")
    private String webAdImgId;//资源的轮播图
    @Column(name = "mobil_ad_img_id")
    private String mobilAdImgId;//资源的轮播图
    @Column(name = "bg_color")
    private String bgColor;//资源需要的背景色
    @Column(name = "is_valid")
    private Boolean isValid;//是否生效 true有效

}
