package com.huajie.educomponent.usercenter.service;

import com.huajie.appbase.BusinessException;
import com.huajie.educomponent.usercenter.dao.AccountJpaRepo;
import com.huajie.educomponent.usercenter.dao.AccountTokenJpaRepo;
import com.huajie.educomponent.usercenter.entity.AccountEntity;
import com.huajie.educomponent.usercenter.entity.AccountTokenEntity;
import com.huajie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckAccountService {

    @Autowired
    private AccountJpaRepo accountJpaRepo;

    @Autowired
    private AccountTokenJpaRepo accountTokenJpaRepo;

    public void checkUserToken(String userId) {
        if(!StringUtils.isNotBlank(userId)) {
            throw new BusinessException("请登录后操作");
        }
        AccountEntity accountEntity = accountJpaRepo.findByUserId(userId);
        if(accountEntity == null) {
            throw new BusinessException("请登录后操作");
        }

        List<AccountTokenEntity> accountEntities = accountTokenJpaRepo.findByAccounts(accountEntity.getId());
        if(accountEntities.isEmpty()){
            throw new BusinessException("请登录后操作");
        }
    }
}
