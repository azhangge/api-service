package com.huajie.educomponent.exam.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.exam.entity.ExamEnrollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by zgz on 2017/9/14.
 */
@Repository
public interface ExamEnrollJpaRepo extends JpaRepository<ExamEnrollEntity, String>{

    @Query("from ExamEnrollEntity where examId=?1 and userId=?2 and deleted=0")
    ExamEnrollEntity findByExamIdAndUserId(String examId, String userId);

    PageResult<ExamEnrollEntity> search(String examId, String userId, int pageIndex, int pageSize);
}
