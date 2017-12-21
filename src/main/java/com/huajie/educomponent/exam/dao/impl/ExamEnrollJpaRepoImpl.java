package com.huajie.educomponent.exam.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.exam.bo.enroll.ExamEnrollBo;
import com.huajie.educomponent.exam.entity.ExamEnrollEntity;
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
 * Created by zgz on 2017/9/14.
 */
public class ExamEnrollJpaRepoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public PageResult<ExamEnrollEntity> search(String examId, String userId, int pageIndex, int pageSize){
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from ExamEnrollEntity where 1=1 and deleted=0";
        StringBuffer whereSb = new StringBuffer();
        if (!StringUtils.isEmpty(examId)){
            whereSb.append(" and examId =:examId");
            queryParam.put("examId", examId);
        }
        if (!StringUtils.isEmpty(userId)){
            whereSb.append(" and userId =:userId");
            queryParam.put("userId", userId);
        }
        String countSql = "select count(1) from ExamEnrollEntity where 1=1 and deleted=0" + whereSb.toString() ;
        String selectSql = sql + whereSb.toString() + " order by date desc";
        PageResult<ExamEnrollEntity> result = new PageResult<ExamEnrollEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<ExamEnrollEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }
}
