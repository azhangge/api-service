package com.huajie.educomponent.studyplan.dao;

import com.huajie.educomponent.studyplan.entity.StudyPlanDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by xuxiaolong on 17-10-31.
 */
@Repository
public interface StudyPlanDetailJpaRepo extends JpaRepository<StudyPlanDetailEntity, String> {

    @Modifying
    @Transactional
    @Query("delete from StudyPlanDetailEntity where planId=?1 and deleted=0")
    void deleteByPlanId(String planId);

    @Query("from StudyPlanDetailEntity where planId=?1 and deleted=0")
    List<StudyPlanDetailEntity>  findByPlanId(String planId);
}
