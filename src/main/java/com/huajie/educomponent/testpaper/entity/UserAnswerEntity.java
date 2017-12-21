package com.huajie.educomponent.testpaper.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by 10070 on 2017/7/26.
 */
@Data
@Entity
@Table(name = "tp_user_answer")
public class UserAnswerEntity extends IdEntity {

    @Column(name = "user_id")
    private String userId;
    @Column(name = "question_set_ins_id")
    private String questionSetInsId;//练习或试卷实例id
    @Column(name = "question_id")
    private String questionId;
    @Column(name = "user_answer")
    private String userAnswer;
    @Column(name = "is_right")
    private Boolean isRight;//true 正确
    @Column(name = "real_score")
    private Float real_score;//todo 改为score
    @Column(name = "submit_time")
    private Date submitTime;
}
