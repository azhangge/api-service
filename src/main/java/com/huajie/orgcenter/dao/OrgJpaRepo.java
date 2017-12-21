package com.huajie.orgcenter.dao;

import com.huajie.orgcenter.entity.OrgEntity;
import com.huajie.orgcenter.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fangxing on 17-7-10.
 */
@Repository
public interface OrgJpaRepo extends JpaRepository<OrgEntity, String> {
    @Query("select e from RoleEntity e where e.id=?1 and e.deleted=0")
    RoleEntity findById(String id);
}