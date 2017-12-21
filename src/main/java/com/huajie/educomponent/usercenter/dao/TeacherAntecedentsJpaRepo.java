package com.huajie.educomponent.usercenter.dao;

import com.huajie.educomponent.usercenter.entity.TeacherAntecedentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 10070 on 2017/8/11.
 */
@Repository
public interface TeacherAntecedentsJpaRepo extends JpaRepository<TeacherAntecedentsEntity, String>{

    @Query("from TeacherAntecedentsEntity t where t.teacherId =?1 and t.deleted = 0 order by t.startTime")
    List<TeacherAntecedentsEntity> findByTeacherBriefId(String teacherBriefId);

    @Query("from TeacherAntecedentsEntity t where t.id =?1 and t.deleted = 0")
    TeacherAntecedentsEntity findById(String id);
}
