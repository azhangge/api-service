package com.huajie.educomponent.exam.dao;

import com.huajie.educomponent.exam.entity.ExamNoticeUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zgz on 2017/9/18.
 */
@Repository
public interface ExamNoticeUserJpaRepo extends JpaRepository<ExamNoticeUserEntity, String>{

    @Query("from ExamNoticeUserEntity where userId=?1 and examId=?2 and deleted=0")
    ExamNoticeUserEntity findByUserIdAndExamId(String userId, String examId);

    @Query("from ExamNoticeUserEntity where examId=?1 and deleted=0")
    ExamNoticeUserEntity findByExamId(String examId);

    @Query("from ExamNoticeUserEntity where userId=?1 and deleted=0")
    List<ExamNoticeUserEntity> findByUserId(String userId);
}
