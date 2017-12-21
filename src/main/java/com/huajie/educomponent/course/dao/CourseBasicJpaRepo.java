package com.huajie.educomponent.course.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.entity.CourseBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * Created by fangxing on 17-7-3.
 */
@Repository
public interface CourseBasicJpaRepo extends JpaRepository<CourseBasicEntity, String> {

    @Query("from CourseBasicEntity e where e.creatorId=?1 and e.deleted=0")
    List<CourseBasicEntity> findByCreatorId(String creatorId);

    @Query("from CourseBasicEntity e where e.creatorId=?1 and e.approveStatus=?2 and e.deleted=0")
    List<CourseBasicEntity> findByCreatorIdAndStatus(String creatorId, Integer approveStatus);

    @Query("from CourseBasicEntity e where e.id=?1 and e.deleted=0")
    CourseBasicEntity findById(String id);

    @Query("from CourseBasicEntity e where e.teacherId=?1 and e.deleted=0")
    List<CourseBasicEntity> findByTeacherId(String teacherId);

    PageResult<CourseBasicEntity> listPages(String queryKeyword, String mainCategoryId, String subCategoryId, Boolean isPublic, Boolean onShelves,
                                            Integer orderKey, Integer order, Integer maxScore, Integer minScore, int pageIndex, int pageSize);

    List<CourseBasicEntity> findByIdIn(List<String> ids);

    @Query("from CourseBasicEntity e where e.deleted=0 order by e.accessCount desc")
    List<CourseBasicEntity> getRecommend();

    @Query("from CourseBasicEntity e where e.teacherId=?1 and e.approveStatus=?2 and e.deleted=0")
    List<CourseBasicEntity> findByTeacherIdAndApproveStatus(String teacherId, Integer approveStatus);

    PageResult<CourseBasicEntity> getCourseSort(Integer sortType, int pageIndex, int pageSize);

    @Query("from CourseBasicEntity e where e.deleted=0 order by e.accessCount desc")
    List<CourseBasicEntity> findHotCourseList();

    //获取课程用户数最多的分类,categoryType 1主 2次
    CourseBasicEntity findCategoryCourseTop(Integer categoryType, String categoryId);

    PageResult<CourseBasicEntity> findByStatus(Integer approveStatus, int pageIndex, int pageSize);

    PageResult<CourseBasicEntity> findByCondition(Map<String,Object> condition, int pageIndex, int pageSize);
}
