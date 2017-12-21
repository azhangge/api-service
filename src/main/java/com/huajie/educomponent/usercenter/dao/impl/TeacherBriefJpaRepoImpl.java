package com.huajie.educomponent.usercenter.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.usercenter.entity.TeacherBriefEntity;
import com.huajie.utils.JpaUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgz on 2017/9/27.
 */
public class TeacherBriefJpaRepoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    PageResult<TeacherBriefEntity> pageLists(Integer approveStatus, int pageIndex, int pageSize) {

        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from TeacherBriefEntity c where 1=1";
        StringBuffer whereSb = new StringBuffer();
        if (approveStatus != null){
            whereSb.append(" and approveStatus =:approveStatus");
            queryParam.put("approveStatus", approveStatus);
        }
        String countSql = "select count(1) from TeacherBriefEntity where 1=1" + whereSb.toString();
        String selectSql = sql + whereSb.toString() + " order by createDate desc";
        PageResult<TeacherBriefEntity> result = new PageResult<TeacherBriefEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<TeacherBriefEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }
}
