package com.huajie.educomponent.pubrefer.dao;

import com.huajie.educomponent.pubrefer.entity.CourseCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fangxing on 17-7-3.
 */
@Repository
public interface CourseCategoryJpaRepo extends JpaRepository<CourseCategoryEntity, String> {
    @Query("select e from CourseCategoryEntity e where e.id=?1 and e.deleted=0")
    CourseCategoryEntity findById(String id);

    @Query("select e from CourseCategoryEntity e where e.code=?1 and e.deleted=0")
    CourseCategoryEntity findByCode(String code);

    @Query("select e from CourseCategoryEntity e where e.deleted=?1 order by e.code")
    List<CourseCategoryEntity> findAllByDeleted(Boolean deleted);

    @Query("select e from CourseCategoryEntity e where e.parentId=?1 order by e.code")
    List<CourseCategoryEntity> findAllByMain(String mainId);
}
