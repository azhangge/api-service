package com.huajie.educomponent.testpaper.entity;

import com.huajie.appbase.BaseEntity;
import io.swagger.annotations.Api;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 10070 on 2017/7/24.
 */
@Api("试卷与题之间的关系")
@Data
@Entity
@Table(name = "tp_question_in")
public class QuestionInEntity extends BaseEntity{

    @Column(name = "question_set_id")
    private String questionSetId;//题集/试卷(模板id, 随机试卷实例时，此处值为实例id)
    @Column(name = "question_id")
    private String questionId;
    @Column(name = "species_id")
    private String speciesId;//
    @Column(name = "question_index")
    private Integer questionIndex;
    @Column(name = "score")
    private float score;
}
