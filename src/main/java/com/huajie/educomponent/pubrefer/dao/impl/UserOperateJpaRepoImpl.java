package com.huajie.educomponent.pubrefer.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.entity.CourseBasicEntity;
import com.huajie.educomponent.pubrefer.constants.UserOperateType;
import com.huajie.educomponent.questionbank.entity.QuestionEntity;
import com.huajie.educomponent.testpaper.entity.QuestionSetEntity;
import com.huajie.utils.JpaUtils;
import com.huajie.utils.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * Created by 10070 on 2017/7/19.
 */
public class UserOperateJpaRepoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    PageResult<Object> listPages(String userId, Integer type, Boolean timeOut, int pageIndex, int pageSize){
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String tableSal = " course_basic";
        if (type == 7){
            tableSal = " qb_question";
        }else if(type == 9){
            tableSal = " tp_question_set";
        }
        String sql = "select * from" + tableSal + " b left join user_operate u on b.id = u.object_id where 1=1";
        StringBuffer whereSb = new StringBuffer();
        if (StringUtils.isNotBlank(userId)){
            whereSb.append(" and u.user_id =:userId");
            queryParam.put("userId", userId);
        }
        if (type != null) {
            whereSb.append(" and u.type =:type");
            queryParam.put("type", type);
            if (type != UserOperateType.USER_LAST_ACCESS.getValue()){
                whereSb.append(" and u.deleted=0");
            }
        }
        if (type == 1 && timeOut == true) {
            whereSb.append(" and b.end_time <:now");
            queryParam.put("now", new Date());
        }
        String countSql = "select count(distinct(u.object_id)) from" + tableSal + " b left join user_operate u on b.id = u.object_id where 1=1" + whereSb.toString();
        if (type != null && type != UserOperateType.USER_LAST_ACCESS.getValue()){
            countSql = countSql + " and u.deleted=0";
        }

        if (type == null || type != UserOperateType.USER_LAST_ACCESS.getValue()){
            whereSb.append(" and u.deleted=0");
        }
        String selectSql = sql + whereSb.toString() + " group by u.object_id order by u.time desc";
        Long count = JpaUtils.countSql(entityManager, countSql, queryParam).longValue();
        PageResult<Object> result = new PageResult<Object>();
        if (count <= 0) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<Object> list = new ArrayList<Object>();
        if (type == 1 || type == 2 || type == 3 || type == 6){
            list = JpaUtils.queryListBySql(entityManager, selectSql, queryParam, pageIndex, pageSize, CourseBasicEntity.class);
        }else if (type == 7){
            list = JpaUtils.queryListBySql(entityManager, selectSql, queryParam, pageIndex, pageSize, QuestionEntity.class);
        }else if (type == 9){
            list = JpaUtils.queryListBySql(entityManager, selectSql, queryParam, pageIndex, pageSize, QuestionSetEntity.class);
        }
        result.setItems(list);
        return result;
    }

}
