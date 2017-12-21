package com.huajie.educomponent.exam.dao;

import com.huajie.educomponent.exam.entity.ExamPaperTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by zgz on 2017/9/18.
 */
@Repository
public interface ExamPaperTemplateJpaRepo extends JpaRepository<ExamPaperTemplateEntity, String> {

    @Query("from ExamPaperTemplateEntity where examId=?1 and deleted=0")
    ExamPaperTemplateEntity findByExamId(String examId);
}
