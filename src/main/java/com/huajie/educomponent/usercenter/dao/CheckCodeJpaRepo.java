package com.huajie.educomponent.usercenter.dao;

import com.huajie.educomponent.usercenter.entity.CheckCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by fangxing on 17-7-3.
 */
@Repository
public interface CheckCodeJpaRepo extends JpaRepository<CheckCodeEntity, String> {
    @Query("select e from CheckCodeEntity e where e.phoneNo=?1 and e.sysId=?2 and e.deleted=0")
    CheckCodeEntity find(String cellphone, String sysId);

    @Query("select e from CheckCodeEntity e where e.phoneNo=?1 and e.code=?2 and e.sysId=?3 and e.deleted=0")
    CheckCodeEntity find(String cellphone, String code, String sysId);
}
