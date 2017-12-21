package com.huajie.educomponent.course.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;

/**
 * Created by fangxing on 17-7-12.
 */
@Data
public class CourseBriefEntity extends IdEntity {

    private String name;
    private String thumbnailId;
    private String teacherId; //用户实名Id
    private String teacherName;
    private String accessCount;
}
