package com.huajie.educomponent.testpaper.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.testpaper.entity.FavoriteQuestionSetEntity;
import com.huajie.utils.JpaUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteQuestionSetJapRepoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    PageResult<FavoriteQuestionSetEntity> listPages(String userId, String search, Integer type, Integer orderBy, int pageIndex, int pageSize){
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from FavoriteQuestionSetEntity where 1=1 and deleted = 0 and newQuestionSetEntity.deleted = 0 ";
        StringBuffer whereSb = new StringBuffer();
        if (!StringUtils.isEmpty(search)) {
            whereSb.append(" and name like :search");
            queryParam.put("search", JpaUtils.wrapLikeParam(search));
        }
        if (type != null) {
            whereSb.append(" and newQuestionSetEntity.type =:type");
            queryParam.put("type", type);
        }
//       /* if (userId != null) {
//            whereSb.append(" and userId =:userId");
//            queryParam.put("userId", userId);
//        }*/
        PageResult<FavoriteQuestionSetEntity> result = new PageResult<FavoriteQuestionSetEntity>();
        String countSql = "select count(1) from FavoriteQuestionSetEntity where 1=1 and deleted = 0 and newQuestionSetEntity.deleted = 0 " + whereSb.toString();
        String selectSql = sql + whereSb.toString();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }

        result.setTotal(count.intValue());
        List<FavoriteQuestionSetEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return  result;
    }
}
