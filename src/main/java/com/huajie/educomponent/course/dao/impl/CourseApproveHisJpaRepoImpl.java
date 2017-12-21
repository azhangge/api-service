package com.huajie.educomponent.course.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.entity.CourseApproveHisEntity;
import com.huajie.educomponent.course.entity.CourseBasicEntity;
import com.huajie.utils.JpaUtils;
import com.huajie.utils.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 10070 on 2017/7/20.
 */
public class CourseApproveHisJpaRepoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public PageResult<CourseBasicEntity> listPages(String approverId, Integer approveStatus, int pageIndex, int pageSize) {

        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "select * from course_basic c left join course_approve_his a on c.id = a.course_id where 1=1 and deleted=0";
        StringBuffer whereSb = new StringBuffer();
        if (approveStatus != null) {
            whereSb.append(" and a.operate =:approveStatus ");
            queryParam.put("approveStatus", approveStatus);
            if (StringUtils.isNotBlank(approverId)) {
                whereSb.append(" and a.approver_id =:approverId");
                queryParam.put("approverId", approverId);
            }
        }
        String countSql = "select count(1) from course_basic c left join course_approve_his a on c.id = a.course_id where 1=1 and deleted=0" + whereSb.toString() + " and a.deleted=0 and c.deleted=0";
        String selectSql = sql + whereSb.toString() + " and a.deleted = 0 and c.deleted=0 order by c.modify_date desc";
        PageResult<CourseBasicEntity> result = new PageResult<CourseBasicEntity>();
        Long count = JpaUtils.countSql(entityManager, countSql, queryParam).longValue();
        if (count <= 0) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<CourseBasicEntity> list = JpaUtils.queryListBySql(entityManager, selectSql, queryParam, pageIndex, pageSize, CourseBasicEntity.class);
        result.setItems(list);
        return result;
    }

    public PageResult<CourseApproveHisEntity> list(String approverId, Integer operate, int pageIndex, int pageSize) {

        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from CourseApproveHisEntity c where 1=1 ";
        StringBuffer whereSb = new StringBuffer();
        if (operate != null) {
            whereSb.append(" and c.operate =:operate ");
            queryParam.put("operate", operate);
        }
        if (StringUtils.isNotBlank(approverId)) {
            whereSb.append(" and c.approverId =:approverId");
            queryParam.put("approverId", approverId);
        }
        String countSql = "select count(1) from CourseApproveHisEntity c where 1=1" + whereSb.toString() + " and c.deleted = 0";
        String selectSql = sql + whereSb.toString() + " and c.deleted = 0 order by c.date desc";
        PageResult<CourseApproveHisEntity> result = new PageResult<CourseApproveHisEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam).longValue();
        if (count <= 0) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<CourseApproveHisEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }

}
