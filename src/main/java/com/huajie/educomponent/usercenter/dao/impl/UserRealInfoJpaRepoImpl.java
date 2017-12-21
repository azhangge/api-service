package com.huajie.educomponent.usercenter.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.exam.entity.ExamEnrollEntity;
import com.huajie.educomponent.usercenter.entity.UserRealInfoEntity;
import com.huajie.utils.JpaUtils;
import org.springframework.util.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgz on 2017/9/29.
 */
public class UserRealInfoJpaRepoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public PageResult<UserRealInfoEntity> search(String realName, Integer approveStatus, int pageIndex, int pageSize){
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from UserRealInfoEntity where 1=1 and deleted=0";
        StringBuffer whereSb = new StringBuffer();
        if (!StringUtils.isEmpty(realName)){
            whereSb.append(" and realName =:realName");
            queryParam.put("realName", JpaUtils.wrapLikeParam(realName));
        }
        if (approveStatus != null){
            whereSb.append(" and approveStatus =:approveStatus");
            queryParam.put("approveStatus", approveStatus);
        }
        String countSql = "select count(1) from UserRealInfoEntity where 1=1 and deleted=0" + whereSb.toString() ;
        String selectSql = sql + whereSb.toString() + " order by createTime desc";
        PageResult<UserRealInfoEntity> result = new PageResult<UserRealInfoEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<UserRealInfoEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }
}
