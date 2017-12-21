package com.huajie.educomponent.questionbank.entity;

import com.huajie.appbase.IdEntity;
import com.huajie.educomponent.questionbank.bo.QuestionTypeBo;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by 10070 on 2017/7/21.
 */
@Data
@Entity
@Table(name = "qb_question_type")
public class QuestionTypeEntity extends IdEntity {

    @Column(name = "question_type")
    private Integer question_type;
    @Column(name = "question_type_name")
    private String questionTypeName;
    @Column(name = "question_type_code")
    private Integer questionTypeCode;
    @Column(name = "creator_id")
    private String creatorId;
    @Column(name = "create_date")
    private Date createDate;

}
