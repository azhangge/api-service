package com.huajie.educomponent.course.dao;

import com.huajie.educomponent.course.entity.CourseChapterUserStatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseChapterUserStatJpaRepo extends JpaRepository<CourseChapterUserStatEntity, String>{

    @Query("from CourseChapterUserStatEntity where chapterId = ?1 and deleted = 0")
    CourseChapterUserStatEntity findByChapterId(String chapterId);

    @Query("from CourseChapterUserStatEntity where userId = ?1 and chapterId = ?2 and deleted = 0")
    CourseChapterUserStatEntity findByUserIdAndChapterId(String userId, String chapterId);
}
