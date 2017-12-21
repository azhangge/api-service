package com.huajie.educomponent.questionbank.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.pubrefer.constants.UserOperateType;
import com.huajie.educomponent.questionbank.constants.UserBanksType;
import com.huajie.educomponent.questionbank.entity.QuestionEntity;
import com.huajie.utils.JpaUtils;
import org.springframework.util.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 10070 on 2017/7/21.
 */
public class QuestionJpaRepoImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public PageResult<QuestionEntity> listPages(String userId, String search, Integer type, Integer isPublic, String mainCategory, String subCategory, int pageIndex, int pageSize) {

        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from QuestionEntity where 1=1 and deleted= 0";
        StringBuffer whereSb = new StringBuffer();
        if (!StringUtils.isEmpty(userId)){
            whereSb.append(" and creatorId =:userId");
            queryParam.put("userId", userId);
        }
        if (!StringUtils.isEmpty(search)) {
            whereSb.append(" and statement like:search");
            queryParam.put("search", JpaUtils.wrapLikeParam(search));
        }
        if (type != null) {
            whereSb.append(" and type =:type");
            queryParam.put("type", type);
        }
        if (isPublic != null) {
            whereSb.append(" and isPublic =:isPublic");
            queryParam.put("isPublic", isPublic);
        }
        if (!StringUtils.isEmpty(mainCategory)) {
            whereSb.append(" and mainCategoryId =:mainCategory");
            queryParam.put("mainCategory", mainCategory);
        }
        if (!StringUtils.isEmpty(subCategory)) {
            whereSb.append(" and subCategoryId =:subCategory");
            queryParam.put("subCategory", subCategory);
        }
        String countSql = "select count(1) from QuestionEntity where 1=1 and deleted= 0" + whereSb.toString() ;
        String selectSql = sql + whereSb.toString() + " order by createDate desc";
        PageResult<QuestionEntity> result = new PageResult<QuestionEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<QuestionEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }

    PageResult<QuestionEntity> userBanks(String userId, Integer action, String search, Integer type, String mainCategory, String subCategory, int pageIndex, int pageSize){

        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "select * from qb_question q left join user_operate u on q.id = u.object_id where 1=1 and q.deleted = 0 and u.deleted = 0";
        StringBuffer whereSb = new StringBuffer();
        if (!StringUtils.isEmpty(search)) {
            whereSb.append(" and q.statement like:search");
            queryParam.put("search", search);
        }
        if (!StringUtils.isEmpty(userId)){
            whereSb.append(" and u.user_id =:userId");
            queryParam.put("userId", userId);
        }
        if (type != null) {
            whereSb.append(" and q.type =:type");
            queryParam.put("type", type);
        }
        if (!StringUtils.isEmpty(mainCategory)) {
            whereSb.append(" and q.mainCategoryId =:mainCategory");
            queryParam.put("mainCategory", mainCategory);
        }
        if (!StringUtils.isEmpty(subCategory)) {
            whereSb.append(" and q.subCategoryId =:subCategory");
            queryParam.put("subCategory", subCategory);
        }
        if (action == UserBanksType.FAVORITE_QUESTION.getValue()){
            whereSb.append(" and u.type =:operateType");
            queryParam.put("operateType", UserOperateType.FAVORITE_QUESTION.getValue());
        }
        String countSql = "select count(1) from qb_question q left join user_operate u on q.id = u.object_id where 1=1 and q.deleted = 0 and u.deleted = 0" + whereSb.toString() ;
        String selectSql = sql + whereSb.toString() + " order by create_date desc";
        PageResult<QuestionEntity> result = new PageResult<QuestionEntity>();
        Long count = JpaUtils.countSql(entityManager, countSql, queryParam).longValue();
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<QuestionEntity> list = JpaUtils.queryListBySql(entityManager, selectSql, queryParam, pageIndex, pageSize, QuestionEntity.class);
        result.setItems(list);
        return result;
    }

}
