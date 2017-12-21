package com.huajie.utils;

import org.hibernate.criterion.Order;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlUtils {

    /**
     * 去除sql的select 子句，未考虑union的情况,用于pagedQuery.
     * <p/>
     * 可以标注从\/\*end\*\/ 开始截取
     * 如果没有标注\/\*end\*\/ ，那么从第一from开始截取
     *
     * @param sql SQL语句
     */
    public static String removeSelect(String sql) {
        if (!StringUtils.isEmpty(sql)) {
            int beginPos = sql.toLowerCase().indexOf("/*end*/");
            if (beginPos == -1) {
                beginPos = sql.toLowerCase().indexOf("from");
            } else {
                beginPos += 7;
            }
            Assert.isTrue(beginPos != -1, " hql : " + sql + " must has a keyword 'from'");
            return sql.substring(beginPos);
        }
        return sql;
    }

    /**
     * 去除sql的orderby 子句，用于pagedQuery.
     *
     * @param sql SQL语句
     */
    public static String removeOrders(String sql) {
        if (!StringUtils.isEmpty(sql)) {
            Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(sql);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                m.appendReplacement(sb, "");
            }
            m.appendTail(sb);
            return sb.toString();
        }
        return sql;
    }

    /**
     * 去除sql的groupby 子句，用于pagedQuery.
     *
     * @param sql SQL语句
     */
    public static String removeGroups(String sql) {
        if (!StringUtils.isEmpty(sql)) {
            Pattern p = Pattern.compile("group\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(sql);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                m.appendReplacement(sb, "");
            }
            m.appendTail(sb);
            return sb.toString();
        }
        return sql;
    }

    /**
     * 设置 Criteria的排序信息
     * 排序信息使用id desc,name,status asc
     *
     * @param orderBy
     */
    public static List<Order> parseOrders(String orderBy) {
        List<Order> result = new ArrayList<Order>();
        if (!StringUtils.isEmpty(orderBy)) {
            String[] arrOrderyBy = orderBy.trim().split(",");
            for (int i = 0; i < arrOrderyBy.length; i++) {
                String name = arrOrderyBy[i].trim();
                String direct = "";
                int pos = name.trim().indexOf(" ");
                if (pos > -1) {
                    direct = name.substring(pos + 1, name.length());
                    name = name.substring(0, pos);
                }
                if ("desc".equalsIgnoreCase(direct.trim())) {
                    result.add(Order.desc(name.trim()));
                } else {
                    result.add(Order.asc(name.trim()));
                }
            }
        }
        return result;
    }

    /**
     * 转义like语句中的
     * <code>'_'</code><code>'%'</code>
     * 将<code>'_'</code>转成sql的<code>'\_'</code>
     * 将<code>'%'</code>转成sql的<code>'\%'</code>
     *
     * @param str
     * @return
     */
    public static String escapeLike(String str) {
        if (!StringUtils.isEmpty(str)) {
            str = StringUtils.replace(str, "\\", "\\\\");
            str = StringUtils.replace(str, "%", "\\%");
            str = StringUtils.replace(str, "_", "\\_");
        }

        return str;
    }
}
