package com.huajie.educomponent.studyplan.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.studyplan.entity.StudyPlanBasicEntity;
import com.huajie.utils.JpaUtils;
import com.huajie.utils.StringUtils;
import org.apache.poi.util.StringUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * Created by xuxiaolong on 2017/10/31.
 */
public class StudyPlanBasicJpaRepoImpl {
    @PersistenceContext
    private EntityManager entityManager;

    PageResult<StudyPlanBasicEntity> listPages(Map<String,String> param) {
        Map<String,Object> queryParam = new HashMap<>();
        String sql = "select * from study_plan_basic s where 1=1 and s.deleted=0";
        StringBuffer whereSb = new StringBuffer();
        StringBuffer orderSb = new StringBuffer();
        if (StringUtils.isNotBlank(param.get("planName"))) {
            whereSb.append(" and s.plan_name like :planName");
            queryParam.put("planName", JpaUtils.wrapLikeParam(param.get("planName")));
        }
        if("0".equals(param.get("isPublish"))){//未发布
            whereSb.append(" and s.start_time > sysdate()");
        }else if("1".equals(param.get("isPublish"))) {//已发布
            whereSb.append(" and s.start_time < sysdate()");
        }
        if (StringUtils.isNotBlank(param.get("userId"))){
            whereSb.append(" and exists(select 1 from study_plan_detail d where d.plan_id = s.id and d.associate_id =:userId)");
            queryParam.put("userId", param.get("userId"));
        }

        orderSb.append(" order by s.end_time asc");

        String countSql = "select count(1) from study_plan_basic s where 1=1 and s.deleted=0 " + whereSb.toString();
        String selectSql = sql + whereSb.toString() + orderSb.toString();
        PageResult<StudyPlanBasicEntity> result = new PageResult<StudyPlanBasicEntity>();
        Long count = JpaUtils.countSql(entityManager, countSql, queryParam).longValue();
        if (count <= 0) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<StudyPlanBasicEntity> list = JpaUtils.queryListBySql(entityManager, selectSql, queryParam,
                Integer.parseInt(param.get("pageIndex")), Integer.parseInt(param.get("pageSize")), StudyPlanBasicEntity.class);
        result.setItems(list);
        return result;
    }
}