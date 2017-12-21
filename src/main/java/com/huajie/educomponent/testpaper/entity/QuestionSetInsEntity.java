package com.huajie.educomponent.testpaper.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by zgz on 2017/9/8.
 */
@Data
@Entity
@Table(name = "question_set_instance")
public class QuestionSetInsEntity extends IdEntity{

    @Column(name = "question_set_id")
    private String questionSetId;
    @Column(name = "type")
    private Integer type;//实例用于 1练习  2考试
    @Column(name = "user_id")
    private String userId;
    @Column(name = "create_time")
    private Date createTime;
}
