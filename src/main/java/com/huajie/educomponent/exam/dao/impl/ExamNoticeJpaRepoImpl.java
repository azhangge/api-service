package com.huajie.educomponent.exam.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.entity.CourseBasicEntity;
import com.huajie.educomponent.course.entity.CourseCommentEntity;
import com.huajie.educomponent.exam.entity.ExamNoticeEntity;
import com.huajie.utils.JpaUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * Created by zgz on 2017/9/7.
 */
public class ExamNoticeJpaRepoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    PageResult<ExamNoticeEntity> getExamNotices(String name, Integer type, Date beginTime, Date endTime, int pageIndex, int pageSize){
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from ExamNoticeEntity where 1=1 and deleted=0";
        StringBuffer whereSb = new StringBuffer();
        if (!StringUtils.isEmpty(name)) {
            whereSb.append(" and noticeName like:name");
            queryParam.put("name", JpaUtils.wrapLikeParam(name));
        }
        if (type != null) {
            whereSb.append(" and type =:type");
            queryParam.put("type", type);
        }
        if (beginTime != null){
            whereSb.append(" and beginTime >=:beginTime");
            queryParam.put("beginTime", beginTime);
        }
        if (endTime != null){
            whereSb.append(" and endTime <=:endTime");
            queryParam.put("endTime", endTime);
        }
        String countSql = "select count(1) from ExamNoticeEntity where 1=1 and deleted=0"+whereSb.toString() ;
        String selectSql = sql + whereSb.toString() + " order by createDate desc";
        PageResult<ExamNoticeEntity> result = new PageResult<ExamNoticeEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<ExamNoticeEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }

    PageResult<ExamNoticeEntity> getUserExamNotices(String userId, int pageIndex, int pageSize){
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "select * from exam_notice e left join exam_notice_user u on e.id=u.exam_id where 1=1 and e.deleted=0 and e.public=1 and e.publish=1";
        StringBuffer whereSb = new StringBuffer();
        StringBuffer orderSb = new StringBuffer();
        if (!StringUtils.isEmpty(userId)) {
            whereSb.append(" and u.user_id =:userId");
            queryParam.put("userId", userId);
        }
        String countSql = "select count(1) from exam_notice e left join exam_notice_user u on e.id=u.exam_id where 1=1 and e.deleted=0 and e.public=1 and e.publish=1" + whereSb.toString() ;
        String selectSql = sql + whereSb.toString() + " order by e.create_date Desc";
        PageResult<ExamNoticeEntity> result = new PageResult<ExamNoticeEntity>();
        Long count = JpaUtils.countSql(entityManager, countSql, queryParam).longValue();
        if (count <= 0) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<ExamNoticeEntity> list = JpaUtils.queryListBySql(entityManager, selectSql, queryParam, pageIndex, pageSize, ExamNoticeEntity.class);
        result.setItems(list);
        return result;
    }

    PageResult<ExamNoticeEntity> getExamNoticesByCondition(Map condition, int pageIndex, int pageSize) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from ExamNoticeEntity where 1=1 and deleted=0";
        StringBuffer whereSb = new StringBuffer();

        if (condition.get("isPublic")!=null) {
            whereSb.append(" and isPublic =:isPublic");
            queryParam.put("isPublic", condition.get("isPublic"));
        }
        if (condition.get("isPublish")!=null) {
            whereSb.append(" and isPublish =:isPublish");
            queryParam.put("isPublish", condition.get("isPublish"));
        }
        if (condition.get("search")!=null) {
            whereSb.append(" and noticeName like :search");
            queryParam.put("search", JpaUtils.wrapLikeParam(condition.get("search").toString()));
        }

        String countSql = "select count(1) from ExamNoticeEntity where 1=1 and deleted=0"+whereSb.toString() ;
        String selectSql = sql + whereSb.toString() + " order by createDate desc";
        PageResult<ExamNoticeEntity> result = new PageResult<ExamNoticeEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<ExamNoticeEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }

    PageResult<ExamNoticeEntity> getPublicAndUserExamNotices(String userId, int pageIndex, int pageSize) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        //String sql = "select * from exam_notice e left join exam_notice_user u on e.id=u.exam_id where 1=1 and e.deleted=0 and e.public=1 and e.publish=1";
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT f.* FROM (")
                .append("SELECT c1.* FROM exam_notice c1 WHERE c1.public = 1 and c1.publish = 1 and c1.deleted = 0");
        if (!StringUtils.isEmpty(userId)) {
            sql.append(" UNION ")
                    .append("SELECT c2.* FROM exam_notice c2, exam_notice_user u ")
                    .append("WHERE c2.public = 0 AND u.exam_id = c2.id and c2.publish = 1 and c2.deleted = 0 and u.deleted =0")
                    .append(" and u.user_id = '").append(userId).append("'");
        }
        sql.append(") f");
        StringBuffer whereSb = new StringBuffer();

        StringBuffer countSql = new StringBuffer();
        //String countSql = "select count(1) from exam_notice e left join exam_notice_user u on e.id=u.exam_id where 1=1 and e.deleted=0 and e.public=1 and e.publish=1" + whereSb.toString() ;
        countSql.append("SELECT count(1) FROM (")
                .append("SELECT c1.* FROM exam_notice c1 WHERE c1.public = 1 and c1.publish = 1 and c1.deleted = 0");
        if (!StringUtils.isEmpty(userId)) {
            countSql.append(" UNION ")
                    .append("SELECT c2.* FROM exam_notice c2, exam_notice_user u ")
                    .append("WHERE c2.public = 0 AND u.exam_id = c2.id and c2.publish = 1 and c2.deleted = 0 and u.deleted =0")
                    .append(" and u.user_id = '").append(userId).append("'");
        }
        countSql.append(") f");

        String selectSql = sql.toString() + whereSb.toString();
        PageResult<ExamNoticeEntity> result = new PageResult<ExamNoticeEntity>();
        Long count = JpaUtils.countSql(entityManager, countSql.toString(), queryParam).longValue();
        if (count <= 0) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<ExamNoticeEntity> list = JpaUtils.queryListBySql(entityManager, selectSql, queryParam, pageIndex, pageSize, ExamNoticeEntity.class);
        result.setItems(list);
        return result;
    }
}
