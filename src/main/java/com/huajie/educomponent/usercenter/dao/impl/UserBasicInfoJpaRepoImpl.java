package com.huajie.educomponent.usercenter.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/8/15.
 */
public class UserBasicInfoJpaRepoImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String findUserInfoByToken(String token){
        List<Object> objects = new ArrayList<Object>();
        String stringSql = new String("select u.id from account_user_basic_info u left join account_account a on u.phone_no = a.phone_no left join account_token t on a.id = t.account_id where t.id =? ");
        objects.add(token);
        List<String> userId = jdbcTemplate.queryForList(stringSql, objects.toArray(), String.class);
        if (userId.size() > 0) {
            return userId.get(0);
        }
        return null;
    }
}
