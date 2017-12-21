package com.huajie.educomponent.testpaper.entity;

import com.huajie.appbase.BaseEntity;
import com.huajie.educomponent.testpaper.bo.QuestionSetBo;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by 10070 on 2017/7/24.
 */
@Api("试卷、题集")
@Data
@Entity
@Table(name = "tp_question_set")
public class QuestionSetEntity extends BaseEntity{

    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private Integer type;
    @Column(name = "public")
    private Boolean isPublic;
    @Column(name = "descriptions")
    private String descriptions;
    @Column(name = "tags")
    private String tags;
    @Column(name = "question_num")
    private Integer questionNum;
    @Column(name = "total_score")
    private Float totalScore;
    @Column(name = "strategy")
    private Integer strategy;//1固定 2随机
    // TODO: 2017/9/22 加发布字段及接口
}
