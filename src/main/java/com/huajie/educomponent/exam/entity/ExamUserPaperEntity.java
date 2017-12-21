package com.huajie.educomponent.exam.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "exam_user_paper")
public class ExamUserPaperEntity extends IdEntity {
    @Column(name = "user_id")
    private String userId;
    @Column(name = "exam_id")
    private String examId;
    @Column(name = "question_set_ins_id")
    private String questionSetInsId;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "submit_time")
    private Date submitTime;
    @Column(name = "score")
    private Float score;
    @Column(name = "pass")
    private Boolean isPass;


}
