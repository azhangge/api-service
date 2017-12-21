package com.huajie.educomponent.course.dao;

import com.huajie.educomponent.course.entity.CourseUserStatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseUserStatJpaRepo extends JpaRepository<CourseUserStatEntity, String>{
}
