package com.huajie.educomponent.course.dao;

import com.huajie.educomponent.course.entity.CourseChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by fangxing on 17-7-3.
 */
@Repository
public interface CourseChapterJpaRepo extends JpaRepository<CourseChapterEntity, String> {

    @Query("from CourseChapterEntity e where e.id=?1 and e.deleted=0")
    CourseChapterEntity findById(String id);

    @Query("from CourseChapterEntity e where e.courseId=?1 and e.deleted=0")
    List<CourseChapterEntity> findByCourseId(String courseId);

    @Query("from CourseChapterEntity e where e.courseId=?1 and e.deleted=0 order by e.chapterIndex")
    List<CourseChapterEntity> findByCourseIdAndDeleted(String courseId);

}
