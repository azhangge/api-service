package com.huajie.educomponent.exam.dao;

import com.huajie.educomponent.exam.entity.ExamScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by zgz on 2017/9/14.
 */
@Repository
public interface ExamScoreJpaRepo extends JpaRepository<ExamScoreEntity,String>{

    @Query("from ExamScoreEntity where examId=?1 and userId=?2 and deleted=0")
    List<ExamScoreEntity> findByExamIdAndUserId(String userId, String examId);
}
