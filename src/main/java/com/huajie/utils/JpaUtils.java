package com.huajie.utils;

import org.thymeleaf.util.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JpaUtils {

    /**
     * 包装like的参数
     *
     * @param originalParam
     * @return
     */
    public static String wrapLikeParam(String originalParam) {
        originalParam = StringUtils.trim(originalParam);
        return "%" + SqlUtils.escapeLike(StringUtils.isEmpty(originalParam) ? "" : originalParam) + "%";
    }

    /**
     * 根据Map设置参数
     *
     * @param query
     * @param paramMap
     */
    private static void setQuery(Query query, Map<String, Object> paramMap) {
        setQuery(query, paramMap, null, null);
    }

    /**
     * 根据Map设置参数
     *
     * @param query
     * @param paramMap
     */
    private static void setQuery(Query query, Map<String, Object> paramMap, Integer pageNo, Integer pageSize) {
        if (paramMap != null && !paramMap.isEmpty()) {
            Iterator<Map.Entry<String, Object>> iterator = paramMap.entrySet().iterator();
            Map.Entry<String, Object> entry;
            while (iterator.hasNext()) {
                entry = iterator.next();
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        if (pageNo != null && pageSize != null) {
            query.setFirstResult(pageNo * pageSize);
            query.setMaxResults(pageSize);
        }
    }

    /**
     * 执行Count语句
     *
     * @param entityManager
     * @param countHql
     * @param paramMap
     * @return
     */
    public static Long count(EntityManager entityManager, String countHql, Map<String, Object> paramMap) {
        Query query = entityManager.createQuery(countHql);
        setQuery(query, paramMap);
        return (Long) query.getSingleResult();
    }

    public static BigInteger countSql(EntityManager entityManager, String countSql, Map<String, Object> paramMap) {
        Query query = entityManager.createNativeQuery(countSql);
        setQuery(query, paramMap);
        return (BigInteger) query.getSingleResult();
    }

    /**
     * 执行Query语句查询List
     *
     * @param entityManager
     * @param queryHql
     * @param paramMap
     * @return
     */
    public static List queryList(EntityManager entityManager, String queryHql, Map<String, Object> paramMap) {
        Query query = entityManager.createQuery(queryHql);
        setQuery(query, paramMap);
        return query.getResultList();
    }

    /**
     * 执行Query语句查询List
     *
     * @param entityManager
     * @param queryHql
     * @param paramMap
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static List queryList(EntityManager entityManager, String queryHql, Map<String, Object> paramMap, Integer pageNo, Integer pageSize) {
        Query query = entityManager.createQuery(queryHql);
        setQuery(query, paramMap, pageNo, pageSize);
        return query.getResultList();
    }

    public static List queryListBySql(EntityManager entityManager, String querySql, Map<String, Object> paramMap, Integer pageNo, Integer pageSize, Class clazz) {
        Query query = entityManager.createNativeQuery(querySql, clazz);
        setQuery(query, paramMap, pageNo, pageSize);
        return query.getResultList();
    }
}
