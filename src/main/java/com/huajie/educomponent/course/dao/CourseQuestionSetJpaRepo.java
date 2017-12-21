package com.huajie.educomponent.course.dao;

import com.huajie.educomponent.course.entity.CourseQuestionSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseQuestionSetJpaRepo extends JpaRepository<CourseQuestionSetEntity, String>{

    @Query("from CourseQuestionSetEntity where courseId = ?1 and deleted = 0")
    List<CourseQuestionSetEntity> findByCourseId(String courseId);
}

