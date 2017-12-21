package com.huajie.educomponent.usercenter.dao;

import com.huajie.educomponent.usercenter.entity.AccountLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 10070 on 2017/8/3.
 */
@Repository
public interface AccountLogJpaRepo extends JpaRepository<AccountLogEntity, String> {
}
