package com.huajie.educomponent.pubrefer.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.entity.CourseBasicEntity;
import com.huajie.educomponent.pubrefer.entity.UserOperateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by fangxing on 17-7-11.
 */
@Repository
public interface UserOperateJpaRepo extends JpaRepository<UserOperateEntity, String> {

    PageResult<Object> listPages(String userId, Integer type, Boolean timeOut, int page, int size);

    @Query("select e from UserOperateEntity e where e.objectId=?1 and e.userId=?2 and e.type=?3 and e.deleted=0")
    UserOperateEntity findByIdAndType(String objectId, String userId, int type);

    @Query("select e from UserOperateEntity e where e.objectId=?1 and e.userId=?2 and e.deleted=0")
    List<UserOperateEntity> findByCourseId(String objectId, String userId);

    @Query("select e from UserOperateEntity e where e.objectId=?1 and e.userId=?2 and e.deleted=0")
    UserOperateEntity findByObjectId(String objectId, String userId);
}
