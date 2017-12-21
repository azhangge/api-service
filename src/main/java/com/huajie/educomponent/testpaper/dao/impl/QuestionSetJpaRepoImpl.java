package com.huajie.educomponent.testpaper.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.testpaper.bo.QuestionSetBo;
import com.huajie.educomponent.testpaper.entity.QuestionSetEntity;
import com.huajie.utils.JpaUtils;
import org.springframework.util.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgz on 2017/7/25.
 */
public class QuestionSetJpaRepoImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public PageResult<QuestionSetEntity> listPages(String userId, Boolean isPublic, String search, Integer type, Boolean self, Integer orderBy, int pageIndex, int pageSize) {

        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from QuestionSetEntity where 1=1 and deleted = 0 ";
        StringBuffer whereSb = new StringBuffer();
        if (!StringUtils.isEmpty(search)) {
            whereSb.append(" and name like :search");
            queryParam.put("search", JpaUtils.wrapLikeParam(search));
        }
        if (isPublic != null) {
            whereSb.append(" and isPublic =:isPublic");
            queryParam.put("isPublic", isPublic);
        }
        if (type != null) {
            whereSb.append(" and type =:type");
            queryParam.put("type", type);
        }
        if (self == true){
            whereSb.append(" and creator_id =:userId");
            queryParam.put("userId", userId);
        }
        if (orderBy != null){
            if (orderBy == 1) {
                whereSb.append(" order by createDate desc");
            }
            if (orderBy == 2){
                whereSb.append(" order by createDate asc");
            }
        }
        String countSql = "select count(1) from QuestionSetEntity where 1=1 and deleted = 0 " + whereSb.toString();
        String selectSql = sql + whereSb.toString();
        PageResult<QuestionSetEntity> result = new PageResult<QuestionSetEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<QuestionSetEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }
}
