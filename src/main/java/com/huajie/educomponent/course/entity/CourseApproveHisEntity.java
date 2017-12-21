package com.huajie.educomponent.course.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fangxing on 17-7-11.
 */
@Data
@Entity
@Table(name = "course_approve_his")
public class CourseApproveHisEntity extends IdEntity {
    @Column(name = "course_id")
    private String courseId;
    @Column(name = "approver_id")
    private String approverId;
    @Column(name = "operate")
    private int operate;
    @Column(name = "reason")
    private String reason;
    @Column(name = "date")
    private Date date;

}
