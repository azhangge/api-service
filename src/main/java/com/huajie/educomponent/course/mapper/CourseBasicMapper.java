package com.huajie.educomponent.course.mapper;

import com.huajie.educomponent.course.entity.CourseBriefEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by fangxing on 17-7-4.
 */
@Mapper
public interface CourseBasicMapper {

    public static final String COL_ID = "id";
    public static final String COL_DELETED = "deleted";
    public static final String COL_MAIN_CATEGORY_ID = "main_category_id";
    public static final String COL_MAIN_CATEGORY_IDS = "main_category_ids";
    public static final String COL_SUB_CATEGORY_ID = "sub_category_id";
    public static final String COL_SUB_CATEGORY_IDS = "sub_category_ids";
    public static final String COL_DETAIL_CATEGORY_ID = "detail_category_id";
    public static final String COL_DETAIL_CATEGORY_IDs = "detail_category_ids";
    public static final String COL_TEACHER_ID = "teacher_id";
    public static final String COL_IS_PUBLIC = "public";
    public static final String COL_IS_ON_SHELVES = "on_shelves";
    public static final String COL_APPROVE_STATUS = "approve_status";

    public static final String ORDER_KEY_COL = "order_col";
    public static final String ORDER_COL_DATE = "modify_date";
    public static final String ORDER_COL_ACCESS_COUNT = "access_count";

    public static final String ORDER_KEY_ORDER = "order_key";
    public static final String ORDER_KEY_ASC = "ASC";
    public static final String ORDER_KEY_DESC = "DESC";

    public static final String PAGE_KEY_INDEX = "page_offset";
    public static final String PAGE_KEY_SIZE = "page_size";

    public static final String SEARCH_KEY = "search";

    public List<CourseBriefEntity> getCourses(Map<String, Object> conditions);
    public Long countCourses(Map<String, Object> conditions);
}
