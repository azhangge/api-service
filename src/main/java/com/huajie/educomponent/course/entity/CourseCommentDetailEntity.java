package com.huajie.educomponent.course.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by fangxing on 17-7-13.
 */
@Data
public class CourseCommentDetailEntity {

    private String id;
    private String userId;
    private String userName;
    private int star;
    private String description;
    private Date date;
}
