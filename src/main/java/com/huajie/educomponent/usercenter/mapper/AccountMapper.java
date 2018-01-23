package com.huajie.educomponent.usercenter.mapper;

import com.huajie.educomponent.usercenter.entity.AccountEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * Created by fangxing on 17-7-4.
 */
@Repository
@Mapper
public interface AccountMapper {
    AccountEntity getAccountById(String id);
}
/*
public interface AccountMapper {
    @Select("SELECT * FROM account")
    @Results({
            @Result(property = "username",  column = "username"),
            @Result(property = "email", column = "email")
    })
    List<AccountEntity> getAll();

    @Insert("INSERT INTO account(username, password, email) VALUES(#{username}, #{password}, #{email})")
    void insert(AccountEntity user);
}
*/