package com.huajie.educomponent.exam.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "exam_enroll")
@Entity
public class ExamEnrollEntity extends IdEntity {
    @Column(name = "user_id")
    private String userId;
    @Column(name = "exam_id")
    private String examId;
    @Column(name = "date")
    private Date date;
    @Column(name = "type")
    private Integer type; //1 在线; 2 线下

}
