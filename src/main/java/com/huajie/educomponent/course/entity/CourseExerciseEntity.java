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
@Table(name = "course_exercise")
public class CourseExerciseEntity extends IdEntity {

    @Column(name = "course_id")
    private String courseId;
    @Column(name = "exercise_id")
    private String exerciseId;
}
