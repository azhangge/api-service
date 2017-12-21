package com.huajie.educomponent.course.dao.impl;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.constants.CourseSortType;
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
 * Created by 10070 on 2017/7/19.
 */
public class CourseBasicJpaRepoImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public PageResult<CourseBasicEntity> listPages(String queryKeyword, String mainCategoryId, String subCategoryId, Boolean isPublic, Boolean onShelves,
                                                   Integer orderKey, Integer order, Integer maxScore, Integer minScore, int pageIndex, int pageSize) {

        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "select * from course_basic c left join teacher_brief t on t.id = c.teacher_id " +
                "left join account_user_basic_info b on b.id = t.user_id left join account_user_real_info u on b.user_real_info_id = u.id where 1=1 and c.deleted=0";
        StringBuffer whereSb = new StringBuffer();
        StringBuffer orderSb = new StringBuffer();
        if (StringUtils.isNotBlank(mainCategoryId)) {
            whereSb.append(" and c.main_category_id =:mainCategoryId");
            queryParam.put("mainCategoryId", mainCategoryId);
        }
        if (StringUtils.isNotBlank(subCategoryId)) {
            whereSb.append(" and c.sub_category_id =:subCategoryId");
            queryParam.put("subCategoryId", subCategoryId);
        }
        if (isPublic != null){
            whereSb.append(" and c.public =:isPublic");
            queryParam.put("isPublic", isPublic);
        }
        if (onShelves != null){
            whereSb.append(" and c.on_shelves =:onShelves");
            queryParam.put("onShelves", onShelves);
        }
        if (StringUtils.isNotBlank(queryKeyword)){
            whereSb.append(" and (c.course_name like:courseName");
            queryParam.put("courseName", JpaUtils.wrapLikeParam(queryKeyword));
        }
        if (StringUtils.isNotBlank(queryKeyword)){
            whereSb.append(" or u.real_name =:realName)");
            queryParam.put("realName", queryKeyword);
        }
        if (orderKey == 0){
            if (order == 1){
                orderSb.append(" order by c.create_date desc");
            }else if (order == 0){
                orderSb.append(" order by c.create_date asc");
            }
        }else if (orderKey == 1){
            if (order == 1){
                orderSb.append(" order by c.access_count desc");
            }else if (order == 0){
                orderSb.append(" order by c.access_count asc");
            }
        }
        String countSql = "select count(1) from course_basic c left join teacher_brief t on t.id = c.teacher_id " +
                "left join account_user_basic_info b on b.id = t.user_id left join account_user_real_info u on b.user_real_info_id = u.id where 1=1 and c.deleted = 0 " + whereSb.toString() ;
        String selectSql = sql + whereSb.toString() + orderSb.toString();
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

    PageResult<CourseBasicEntity> getCourseSort(Integer sortType, int pageIndex, int pageSize){
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from CourseBasicEntity where 1=1 and deleted=0";
        StringBuffer whereSb = new StringBuffer();
        if (sortType != null) {
            if (sortType == CourseSortType.NEWEST.getValue()){
                whereSb.append(" order by createTime desc");
            }
            if (sortType == CourseSortType.HOTEST.getValue()){
                whereSb.append(" order by accessCount desc");
            }
        }
        String countSql = "select count(1) from CourseBasicEntity";
        String selectSql = sql + whereSb.toString();
        PageResult<CourseBasicEntity> result = new PageResult<CourseBasicEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<CourseBasicEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }

    CourseBasicEntity findCategoryCourseTop(Integer categoryType, String categoryId){
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "select * from course_basic c where 1=1 and deleted=0 and public = 1 and on_shelves=1";
        StringBuffer whereSb = new StringBuffer();
        if (StringUtils.isNotBlank(categoryId)) {
            if (categoryType == 1) {
                whereSb.append(" and c.access_count = (select max(b.access_count) from course_basic b where b.main_category_id =:categoryId and c.main_category_id = b.main_category_id)");
                queryParam.put("categoryId", categoryId);
            }
            if (categoryType == 2) {
                whereSb.append(" and c.access_count = (select max(b.access_count) from course_basic b where b.sub_category_id =:categoryId and c.sub_category_id = b.sub_category_id)");
                queryParam.put("categoryId", categoryId);
            }
        }

        List<CourseBasicEntity> list = JpaUtils.queryListBySql(entityManager, sql + whereSb, queryParam, 0, 1, CourseBasicEntity.class);
        if (list.size() < 1){
            return null;
        }
        return list.get(0);
    }

    PageResult<CourseBasicEntity> findByStatus(Integer status, int pageIndex, int pageSize){
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from CourseBasicEntity where 1=1 and deleted = 0";
        StringBuffer whereSb = new StringBuffer();
        if (status != null) {
            if (status == 2) {
                whereSb.append(" and approveStatus =:status");
                queryParam.put("status", status);
            }
        }
        String countSql = "select count(1) from CourseBasicEntity where 1=1 and deleted = 0" + whereSb.toString();
        String selectSql = sql + whereSb.toString();
        PageResult<CourseBasicEntity> result = new PageResult<CourseBasicEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<CourseBasicEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }

    PageResult<CourseBasicEntity> findByCondition(Map<String,Object> condition, int pageIndex, int pageSize){
        Map<String, Object> queryParam = new HashMap<String, Object>();
        String sql = "from CourseBasicEntity where 1=1 and deleted = 0";
        StringBuffer whereSb = new StringBuffer();
        if (condition.get("isOnShelves")!=null) {
                whereSb.append(" and isOnShelves =:isOnShelves");
                queryParam.put("isOnShelves", condition.get("isOnShelves"));
        }
        if (condition.get("isPublic")!=null) {
            whereSb.append(" and isPublic =:isPublic");
            queryParam.put("isPublic", condition.get("isPublic"));
        }
        if (condition.get("search")!=null) {
            whereSb.append(" and courseName like :search");
            queryParam.put("search", JpaUtils.wrapLikeParam(condition.get("search").toString()));
        }
        String countSql = "select count(1) from CourseBasicEntity where 1=1 and deleted = 0" + whereSb.toString();
        String selectSql = sql + whereSb.toString();
        PageResult<CourseBasicEntity> result = new PageResult<CourseBasicEntity>();
        Long count = JpaUtils.count(entityManager, countSql, queryParam);
        if (count <= 0l) {
            result.setItems(Collections.EMPTY_LIST);
            return result;
        }
        result.setTotal(count.intValue());
        List<CourseBasicEntity> list = JpaUtils.queryList(entityManager, selectSql, queryParam, pageIndex, pageSize);
        result.setItems(list);
        return result;
    }
}
