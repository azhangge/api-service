package com.huajie.educomponent.course.entity;

import com.huajie.appbase.BaseEntity;
import com.huajie.educomponent.course.bo.CourseCommentBo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fangxing on 17-7-11.
 */
@Data
@Entity
@Table(name = "course_comment")
public class CourseCommentEntity extends BaseEntity {

    @Column(name = "course_id")
    private String courseId;
    @Column(name = "star")
    private int star;
    @Column(name = "comment")
    private String comment;
}
