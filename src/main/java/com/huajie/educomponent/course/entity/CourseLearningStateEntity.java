package com.huajie.educomponent.course.entity;

import com.huajie.appbase.IdEntity;
import io.swagger.annotations.Api;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fangxing on 17-7-11.
 */

@Api("学习进度，只计算视频课程的")
@Data
@Entity
@Table(name = "course_learning_state")
public class CourseLearningStateEntity extends IdEntity {

    @Column(name = "course_id")
    private String courseId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "resource_id")
    private String resourceId;
    @Column(name = "start_point")
    private Integer startPoint;//上次的进度
    @Column(name = "last_time")
    private Date lastTime;
}
