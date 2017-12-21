package com.huajie.educomponent.course.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "course_question_set")
public class CourseQuestionSetEntity extends IdEntity{

    @Column(name = "course_id")
    private String courseId;
    @Column(name = "question_set_id")
    private String questionSetId;
    @Column(name = "create_time")
    private Date createTime;
}
