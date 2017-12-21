package com.huajie.educomponent.course.entity;

import com.huajie.appbase.BaseEntity;
import com.huajie.educomponent.course.bo.CourseBasicBo;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fangxing on 17-7-5.
 */
@Data
@Entity
@Table(name = "course_basic")
public class CourseBasicEntity extends BaseEntity {
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "thumbnail_id")
    private String thumbnailId;
    @Column(name = "main_category_id")
    private String mainCategoryId;
    @Column(name = "sub_category_id")
    private String subCategoryId;
    @Column(name = "detail_category_id")
    private String detailCategoryId; //第三级分类。预留
    @Column(name = "chapter_type")
    private Integer chapterType;//1章节 2节
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "descriptions", columnDefinition = "CLOB")
    private String descriptions;
    @Column(name = "teacher_id")
    private String teacherId; //实际是用户实名Id
    @Column(name = "tags")
    private String tags;//以下划线为分割符的字符串。
    @Column(name = "public")
    private Boolean isPublic;
    @Column(name = "on_shelves")
    private Boolean isOnShelves;
    @Column(name = "approve_status")
    private Integer approveStatus;
    @Column(name = "access_count")
    private Integer accessCount;
    @Column(name = "class_hour")
    private Float classHour;
}
