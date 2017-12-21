package com.huajie.educomponent.exam.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.exam.entity.ExamUserPaperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zgz on 2017/9/20.
 */
@Repository
public interface ExamUserPaperJpaRepo extends JpaRepository<ExamUserPaperEntity, String>{

    @Query("from ExamUserPaperEntity where examId=?1 and deleted=0 order by score desc")
    List<ExamUserPaperEntity> findByExamId(String examId);

    @Query("from ExamUserPaperEntity where questionSetInsId=?1 and deleted=0")
    ExamUserPaperEntity findByInsId(String questionSetInsId);

    PageResult<ExamUserPaperEntity> findByUserId(String userId, int pageIndex, int pageSize);

    PageResult<ExamUserPaperEntity> findByIns(String examId, int pageIndex, int pageSize);
}
