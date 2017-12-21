package com.huajie.educomponent.usercenter.dao;

import com.huajie.educomponent.usercenter.entity.AccountRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by zgz on 2017/9/29.
 */
@Repository
public interface AccountRoleJpaRepo extends JpaRepository<AccountRoleEntity, String>{

    @Query("from AccountRoleEntity where userId=?1 and deleted=0")
    AccountRoleEntity findByUserId(String userId);
}
