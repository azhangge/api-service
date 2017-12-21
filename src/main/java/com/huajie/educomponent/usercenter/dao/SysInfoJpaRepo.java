package com.huajie.educomponent.usercenter.dao;


import com.huajie.educomponent.usercenter.entity.SysInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by fangxing on 17-7-3.
 */
@Repository
public interface SysInfoJpaRepo extends JpaRepository<SysInfoEntity, String> {

    @Query("select a from SysInfoEntity a where a.sysHost=?1 and a.sysPort=?2 and a.deleted=0")
    SysInfoEntity find(String host, int port);
}
