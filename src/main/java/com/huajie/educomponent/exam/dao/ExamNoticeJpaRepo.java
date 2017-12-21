package com.huajie.educomponent.exam.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.exam.entity.ExamNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.Map;

/**
 * Created by zgz on 2017/9/7.
 */
@Repository
public interface ExamNoticeJpaRepo extends JpaRepository<ExamNoticeEntity, String>{

    PageResult<ExamNoticeEntity> getExamNotices(String name, Integer type, Date beginTime, Date endTime, int pageIndex, int pageSize);

    PageResult<ExamNoticeEntity> getUserExamNotices(String userId, int pageIndex, int pageSize);

    PageResult<ExamNoticeEntity> getExamNoticesByCondition(Map condition, int pageIndex, int pageSize);

    PageResult<ExamNoticeEntity> getPublicAndUserExamNotices(String userId, int pageIndex, int pageSize);
}
