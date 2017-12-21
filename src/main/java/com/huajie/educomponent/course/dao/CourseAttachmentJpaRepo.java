package com.huajie.educomponent.course.dao;

import com.huajie.educomponent.course.entity.CourseAttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by 10070 on 2017/7/20.
 */
@Repository
public interface CourseAttachmentJpaRepo extends JpaRepository<CourseAttachmentEntity, String>{

    @Query("from CourseAttachmentEntity c where c.courseId=?1 and deleted=0")
    CourseAttachmentEntity findByCourseId(String courseId);
}
