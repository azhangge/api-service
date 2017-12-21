package com.huajie.educomponent.exam.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.exam.entity.ExamUserPaperEntity;
import com.huajie.utils.JpaUtils;
import org.springframework.util.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * Created by zgz on 2017/9/26.
 */
public class ExamUserPaperJpaRepoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    PageResult<ExamUserPaperEntity> findByUserId(String userId, int pageIndex, int pageSize) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "select * from exam_user_paper where 1=1 and deleted=0";
        StringBuffer whereSb = new StringBuffer();
        if (!StringUtils.isEmpty(userId)) {
            whereSb.append(" and user_id =:userId");
            queryParam.put("userId", userId);
        }
        String countSql = "select count(distinct(exam_id)) from exam_user_paper where 1=1" + whereSb.toString();
        String selectSql = sql + whereSb.toString() + " group by exam_id order by submit_time desc";
        PageResult<ExamUserPaperEntity> result = new PageResult<ExamUserPaperEntity>();
        Long count = JpaUtils.countSql(entityManager, countSql, queryParam).longValue();
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<ExamUserPaperEntity> list = JpaUtils.queryListBySql(entityManager, selectSql, queryParam, pageIndex, pageSize, ExamUserPaperEntity.class);
        result.setItems(list);
        return result;
    }

    PageResult<ExamUserPaperEntity> findByIns(String examId, int pageIndex, int pageSize) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from ExamUserPaperEntity where 1=1 and deleted=0";
        StringBuffer whereSb = new StringBuffer();
        if (!StringUtils.isEmpty(examId)) {
            whereSb.append(" and examId =:examId");
            queryParam.put("examId", examId);
        }
        String countSql = "select count(1) from ExamUserPaperEntity where 1=1 and deleted=0" + whereSb.toString();
        String selectSql = sql + whereSb.toString() + " order by score desc";
        PageResult<ExamUserPaperEntity> result = new PageResult<ExamUserPaperEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<ExamUserPaperEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }
}
