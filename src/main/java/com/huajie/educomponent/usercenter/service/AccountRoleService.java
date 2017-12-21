package com.huajie.educomponent.usercenter.service;

import com.huajie.educomponent.usercenter.constans.AccountRoleType;
import com.huajie.educomponent.usercenter.dao.AccountRoleJpaRepo;
import com.huajie.educomponent.usercenter.entity.AccountRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * Created by zgz on 2017/9/29.
 */
@Service
public class AccountRoleService {

    @Autowired
    private AccountRoleJpaRepo accountRoleJpaRepo;

    /**
     * 设置用户角色
     * @param userId
     * @param type
     */
    public void setUserRole(String userId, Integer type){
        AccountRoleEntity accountRoleEntity = new AccountRoleEntity();
        accountRoleEntity.setUserId(userId);
        accountRoleEntity.setRoleType(AccountRoleType.NORMAL.getValue());
        accountRoleEntity.setCreateTime(new Date());
        accountRoleJpaRepo.save(accountRoleEntity);
    }
}
