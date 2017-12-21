package com.huajie.orgcenter.dao;

import com.huajie.orgcenter.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fangxing on 17-7-10.
 */
@Repository
public interface GroupJpaRepo extends JpaRepository<GroupEntity, String> {
    @Query("select e from GroupEntity e where e.id=?1 and e.deleted=0")
    GroupEntity findById(String id);

    @Query("select e from GroupEntity e where e.orgId=?1 and e.deleted=0")
    List<GroupEntity> findByOrgId(String orgId);
}