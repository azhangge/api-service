package com.huajie.educomponent.testpaper.entity;

import com.huajie.appbase.BaseEntity;
import io.swagger.annotations.Api;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Api("大题")
@Data
@Entity
@Table(name = "tp_paper_ques_species")
public class QuestionSpeciesEntity extends BaseEntity {

    @Column(name = "question_set_id")
    private String questionSetId;
    @Column(name = "type")
    private Integer type;
    @Column(name = "descriptions")
    private String descriptions;
    @Column(name = "species_index")
    private Integer speciesIndex;
    @Column(name = "question_num")
    private Integer questionNum;
    @Column(name = "species_score")
    private Float speciesScore;

}
