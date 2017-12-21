package com.huajie.educomponent.usercenter.dao;

import com.huajie.educomponent.usercenter.entity.AccountTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 10070 on 2017/8/2.
 */
@Repository
public interface AccountTokenJpaRepo extends JpaRepository<AccountTokenEntity, String>{

    @Query("from AccountTokenEntity a where a.accountId =?1")
    List<AccountTokenEntity> findByAccountId(String accountId);

    @Query("from AccountTokenEntity a where a.accountId =?1 and deleted=0")
    List<AccountTokenEntity> findByAccounts(String accountId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("delete from AccountTokenEntity a where a.accountId =?1")
    void deleteByAccountId(String accountId);

    @Query("from AccountTokenEntity a where a.accountId =?1 and a.device =?2 and deleted=0")
    List<AccountTokenEntity> findByAccountIdAndDevice(String accountId, Integer device);
}
