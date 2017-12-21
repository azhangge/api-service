package com.huajie.educomponent.course.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.entity.CourseCommentEntity;
import com.huajie.utils.JpaUtils;
import com.huajie.utils.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 10070 on 2017/7/19.
 */
public class CourseCommentJpaRepoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public PageResult<CourseCommentEntity> listPages(String courseId, int page, int size) {
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from CourseCommentEntity where 1=1 and deleted=0";
        StringBuffer whereSb = new StringBuffer();
        if (StringUtils.isNotBlank(courseId)) {
            whereSb.append(" and courseId =:courseId");
            queryParam.put("courseId", courseId);
        }
        String countSql = "select count(1) from CourseCommentEntity where 1=1 and deleted=0"+whereSb.toString() ;
        String selectSql = sql + whereSb.toString() + " order by createDate desc";
        PageResult<CourseCommentEntity> result = new PageResult<CourseCommentEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<CourseCommentEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, page, size);
        result.setItems(list);
        return result;
    }
}
