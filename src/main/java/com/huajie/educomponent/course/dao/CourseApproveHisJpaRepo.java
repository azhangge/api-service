package com.huajie.educomponent.course.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.entity.CourseApproveHisEntity;
import com.huajie.educomponent.course.entity.CourseBasicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by fangxing on 17-7-11.
 */
@Repository
public interface CourseApproveHisJpaRepo extends JpaRepository<CourseApproveHisEntity, String> {

    @Query("select e from CourseApproveHisEntity e where e.id=?1 and e.deleted=0")
    CourseApproveHisEntity findById(String id);

    @Query("select e from CourseApproveHisEntity e where e.courseId=?1 and e.deleted=0")
    CourseApproveHisEntity findByCourseId(String id);

    PageResult<CourseBasicEntity> listPages(String approverId, Integer approveStatus, int page, int size);

    PageResult<CourseApproveHisEntity> list(String approverId, Integer operate, int pageIndex, int pageSize);

    @Query("select e from CourseApproveHisEntity e where e.courseId=?1 and e.operate =?2 and e.deleted=0")
    CourseApproveHisEntity findByCourseIdandOperate(String id, int operate);
}