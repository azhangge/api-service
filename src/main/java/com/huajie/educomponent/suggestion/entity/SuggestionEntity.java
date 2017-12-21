package com.huajie.educomponent.suggestion.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zgz on 2017/9/6.
 */
@Data
@Entity
@Table(name = "suggestion")
public class SuggestionEntity extends IdEntity{

    @Column(name = "user_id")
    private String userId;
    @Column(name = "suggestion")
    private String suggestion;//内容
    @Column(name = "pic_ids")
    private String picIds;//图片id下划线隔开
    @Column(name = "type")
    private Integer type;// 1 课程  2 考试 3 练习 4 其他
    @Column(name = "create_date")
    private Date createDate;
}
