package com.huajie.educomponent.course.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "course_chapter_user_stat")
public class CourseChapterUserStatEntity extends IdEntity{

    @Column(name = "chapter_id")
    private String chapterId;//节id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "last_study_time")
    private Integer lastStudyTime;//上次学习时长(秒)
    @Column(name = "last_time")
    private Date lastTime;//上次结束学习的时间
    @Column(name = "total_study_time")
    private Integer totalStudyTime;//累计已学时长（秒）
}
