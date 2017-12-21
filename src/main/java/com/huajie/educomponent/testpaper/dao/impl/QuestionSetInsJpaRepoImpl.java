package com.huajie.educomponent.testpaper.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.entity.CourseCommentEntity;
import com.huajie.educomponent.testpaper.entity.QuestionSetInsEntity;
import com.huajie.utils.JpaUtils;
import com.huajie.utils.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2017/9/20.
 */
public class QuestionSetInsJpaRepoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public PageResult<QuestionSetInsEntity> listBySetId(String userId, Integer type, int pageIndex, int pageSize){
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from QuestionSetInsEntity where 1=1 and deleted=0";
        StringBuffer whereSb = new StringBuffer();
        if (StringUtils.isNotBlank(userId)) {
            whereSb.append(" and userId =:userId");
            queryParam.put("userId", userId);
        }
        if (type != null){
            whereSb.append(" and type =:type");
            queryParam.put("type", type);
        }
        String countSql = "select count(1) from QuestionSetInsEntity where 1=1 and deleted=0"+whereSb.toString() ;
        String selectSql = sql + whereSb.toString() + " order by createTime desc";
        PageResult<QuestionSetInsEntity> result = new PageResult<QuestionSetInsEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<QuestionSetInsEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }
}
