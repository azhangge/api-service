package com.huajie.educomponent.course.dao;

import com.huajie.educomponent.course.entity.CourseLearningStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 10070 on 2017/7/19.
 */
@Repository
public interface CourseLearningStateJpaRepo extends JpaRepository<CourseLearningStateEntity, String>{
}
