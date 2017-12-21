package com.huajie.educomponent.course.entity;

import com.huajie.appbase.BaseEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fangxing on 17-7-11.
 */

@Data
@Entity
@Table(name = "course_chapter")
public class CourseChapterEntity extends BaseEntity {

    @Column(name = "course_id")
    private String courseId;
    @Column(name = "name")
    private String name;
    @Column(name = "chapter_index")
    private int chapterIndex;
    @Column(name = "second")
    private int second;
    @Column(name = "parent_id")
    private String parentId;
    @Column(name = "descriptions")
    private String descriptions;
    @Column(name = "tags")
    private String tags;
    @Column(name = "resource_id")
    private String resourceId;
    @Column(name = "resource_type")
    private Integer resourceType;
}
