package com.huajie.educomponent.testpaper.entity;

import com.huajie.appbase.BaseEntity;
import io.swagger.annotations.Api;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by 10070 on 2017/7/24.
 */
@Api("试卷、题集")
@Data
@Entity
@Table(name = "tp_question_set")
public class NewQuestionSetEntity{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid") //系统自带的uuid生成器就能达到目的，没有中划线
    @Column(name = "id")
    private String id;
    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Integer type;
}
