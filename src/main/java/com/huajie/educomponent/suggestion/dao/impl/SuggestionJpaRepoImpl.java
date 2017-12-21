package com.huajie.educomponent.suggestion.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.suggestion.entity.SuggestionEntity;
import com.huajie.utils.JpaUtils;
import org.springframework.util.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgz on 2017/9/6.
 */
public class SuggestionJpaRepoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public PageResult<SuggestionEntity> listPages(String userId, String keyword, Integer type, int pageIndex, int pageSize) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from SuggestionEntity where 1=1 and deleted=0";
        StringBuffer whereSb = new StringBuffer();
        if (!StringUtils.isEmpty(userId)) {
            whereSb.append(" and userId =:userId");
            queryParam.put("userId", userId);
        }
        if (!StringUtils.isEmpty(keyword)) {
            whereSb.append(" and suggestion like:keyword");
            queryParam.put("keyword", JpaUtils.wrapLikeParam(keyword));
        }
        if (type != null) {
            whereSb.append(" and type =:type");
            queryParam.put("type", type);
        }
        String countSql = "select count(1) from SuggestionEntity where 1=1 and deleted=0"+whereSb.toString() ;
        String selectSql = sql + whereSb.toString() + " order by createDate desc";
        PageResult<SuggestionEntity> result = new PageResult<SuggestionEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<SuggestionEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }
}
