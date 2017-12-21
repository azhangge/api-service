package com.huajie.educomponent.log;

import com.huajie.educomponent.usercenter.dao.AccountLogJpaRepo;
import com.huajie.educomponent.usercenter.entity.AccountLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * Created by Lenovo on 2017/8/17.
 */
@Service
public class LogService {

    @Autowired
    private AccountLogJpaRepo accountLogJpaRepo;

    /**
     * 保存账户操作记录记录
     * @param accountId type
     */
    public void saveAccountLog(String accountId, Integer type){
        AccountLogEntity accountLog = new AccountLogEntity();
        accountLog.setAccountId(accountId);
        accountLog.setOpType(type);
        accountLog.setDate(new Date());
        accountLogJpaRepo.save(accountLog);
    }
}
