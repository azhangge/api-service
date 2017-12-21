package com.huajie.educomponent.course.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fangxing on 17-7-11.
 */
@Data
@Entity
@Table(name = "course_attachment")
public class CourseAttachmentEntity extends IdEntity {
    @Column(name = "course_id")
    private String courseId;
    @Column(name = "course_attachment_id")
    private String courseAttachmentId;

}
