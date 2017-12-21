package com.huajie.educomponent.course.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Created by zgz on 2017/9/9.
 */
@Data
public class CourseCreateBo {
    private String courseId;
    private String courseName;
    private String thumbnailId;
    private String mainCategoryId;
    private String subCategoryId;
    private String detailCategoryId;
    private Integer chapterType;
    private String descriptions;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date endTime;
    private String teacherId;//用户实名id
    private String tags;
    private Boolean isPublic;
    private int courseSecond;
    private int score;
    private String thumbnailPath;
    private float classHour;
}
