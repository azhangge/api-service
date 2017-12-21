package com.huajie.educomponent.course.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.entity.CourseCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by fangxing on 17-7-3.
 */
@Repository
public interface CourseCommentJpaRepo extends JpaRepository<CourseCommentEntity, String> {
    @Query("select e from CourseCommentEntity e where e.courseId=?1 and e.deleted=0")
    List<CourseCommentEntity> findByCourseId(String courseId);

    @Query("select e from CourseCommentEntity e where e.courseId=?1 and e.creatorId=?2 and e.deleted=0")
    CourseCommentEntity findByCourseIdAndUserId(String courseId, String creatorId);

    PageResult<CourseCommentEntity> listPages(String courseId, int page, int size);
}
