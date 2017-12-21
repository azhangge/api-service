package com.huajie.educomponent.course.mapper;

import com.huajie.educomponent.course.entity.CourseCommentDetailEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by fangxing on 17-7-4.
 */
@Mapper
public interface CourseCommentMapper {
    public static final String COL_COURSE_ID = "course_id";

    public static final String ORDER_KEY_COL = "order_col";
    public static final String ORDER_COL_CREATE_DATE = "create_date";


    public static final String ORDER_KEY_ORDER = "order_key";
    public static final String ORDER_KEY_ASC = "ASC";
    public static final String ORDER_KEY_DESC = "DESC";

    public static final String PAGE_KEY_INDEX = "page_offset";
    public static final String PAGE_KEY_SIZE = "page_size";

    public static final String SEARCH_KEY = "search";

    public List<CourseCommentDetailEntity> getCourseComments(Map<String, Object> conditions);
    public Long countCourseComments(Map<String, Object> conditions);
}
