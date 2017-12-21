package com.huajie.educomponent.exam.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by zgz on 2017/9/14.
 */
@Data
@Entity
@Table(name = "exam_score")
public class ExamScoreEntity extends IdEntity{

    @Column(name = "exam_id")
    private String examId;
    @Column(name = "question_set_ins_id")
    private String questionSetInsId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "max_score")
    private String maxScore;
}
