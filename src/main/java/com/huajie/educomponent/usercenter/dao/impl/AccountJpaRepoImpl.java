package com.huajie.educomponent.usercenter.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.usercenter.entity.AccountEntity;
import com.huajie.utils.JpaUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 10070 on 2017/7/31.
 */
public class AccountJpaRepoImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public PageResult<AccountEntity> listPages(int page, int size) {

        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from CourseBasicEntity c where 1=1";
        StringBuffer whereSb = new StringBuffer();
        String countSql = "select count(1) from AccountEntity where 1=1" + whereSb.toString();
        String selectSql = sql + whereSb.toString() + " order by createDate desc";
        PageResult<AccountEntity> result = new PageResult<AccountEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<AccountEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, page, size);
        result.setItems(list);
        return result;
    }
}
