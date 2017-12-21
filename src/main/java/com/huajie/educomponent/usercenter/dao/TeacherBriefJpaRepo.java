package com.huajie.educomponent.usercenter.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.usercenter.entity.TeacherBriefEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by 10070 on 2017/8/12.
 */

@Repository
public interface TeacherBriefJpaRepo extends JpaRepository<TeacherBriefEntity, String> {

    @Query("from TeacherBriefEntity  e where e.userId=?1 and e.deleted=0")
    TeacherBriefEntity findByUserRealInfoId(String userId);

    @Query("select e from TeacherBriefEntity e where e.deleted=?1 order by e.createDate")
    List<TeacherBriefEntity> findAllByDeleted(Boolean deleted);

    PageResult<TeacherBriefEntity> pageLists(Integer approveStatus, int pageIndex, int pageSize);
}
