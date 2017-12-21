package com.huajie.educomponent.course.bo;

import lombok.Data;

import java.util.Date;

@Data
public class CourseChapterStudyDetailBo {

    private String chapterId;
    private String userId;
    private Integer lastStudyTime;
    private Date lastTime;
    private Integer totalStudyTime;
}
