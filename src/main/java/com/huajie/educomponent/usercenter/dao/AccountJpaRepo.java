package com.huajie.educomponent.usercenter.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.usercenter.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fangxing on 17-7-3.
 */
@Repository
public interface AccountJpaRepo extends JpaRepository<AccountEntity, String> {

    @Query("select e from AccountEntity e where e.phoneNo=?1 and e.deleted=0")
    List<AccountEntity> findByCellPhone(String cellphone);

    @Query("select e from AccountEntity e where e.username=?1 and e.deleted=0")
    List<AccountEntity> findByUsername(String username);

    @Query("select e from AccountEntity e where e.phoneNo=?1 and e.sysId=?2 and e.deleted=0")
    AccountEntity findByCellAndSysId(String cellphone, String sysId);

    @Query("select e from AccountEntity e where e.username=?1 and e.sysId=?2 and e.deleted=0")
    AccountEntity findByUsernameAndSysId(String userName, String sysId);

    @Query("select e from AccountEntity e where e.id=?1 and e.deleted=0")
    AccountEntity findById(String id);

    @Query("select e from AccountEntity e where e.userId=?1 and e.deleted=0")
    AccountEntity findByUserId(String userId);

    @Query("select e from AccountEntity e where e.phoneNo=?1 and e.password=?2 and e.sysId=?3 and e.deleted=0")
    List<AccountEntity> findByCellphoneAndPassword(String cellphone, String password);

    @Query("update AccountEntity e set e.deleted=1 where e.sysId =?1")
    @Modifying
    void deleteBySysId(String sysId);

    PageResult<AccountEntity> listPages(int page, int size);

}
