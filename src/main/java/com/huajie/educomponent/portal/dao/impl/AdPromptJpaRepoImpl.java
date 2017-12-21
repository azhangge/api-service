package com.huajie.educomponent.portal.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.portal.entity.AdPromptEntity;
import com.huajie.utils.JpaUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdPromptJpaRepoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    PageResult<AdPromptEntity> findByCondition(Map<String,Object> condition, int pageIndex, int pageSize) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from AdPromptEntity where 1=1 and deleted = 0";
        StringBuffer whereSb = new StringBuffer();
        if (condition.get("resourceType")!=null) {
            whereSb.append(" and resourceType =:resourceType");
            queryParam.put("resourceType", condition.get("resourceType"));
        }
        if (condition.get("search")!=null) {
            whereSb.append(" and name like :search");
            queryParam.put("search", JpaUtils.wrapLikeParam(condition.get("search").toString()));
        }
        String countSql = "select count(1) from AdPromptEntity where 1=1 and deleted = 0" + whereSb.toString();
        String selectSql = sql + whereSb.toString();
        PageResult<AdPromptEntity> result = new PageResult<AdPromptEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<AdPromptEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }
}
