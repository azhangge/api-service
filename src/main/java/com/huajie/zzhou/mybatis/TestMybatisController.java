package com.huajie.zzhou.mybatis;

import com.huajie.educomponent.usercenter.entity.AccountEntity;
import com.huajie.educomponent.usercenter.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestMybatisController {

    @Autowired
    private AccountMapper accountMapper;

    @RequestMapping(value = "/mybatis", method = RequestMethod.GET)
    public AccountEntity test(){
//        return accountMapper.getAccountById("3b6348fd-0785-4364-a45d-0ed5cd35433f");
        return null;
    }
}
