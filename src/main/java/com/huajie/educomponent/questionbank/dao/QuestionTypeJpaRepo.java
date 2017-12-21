package com.huajie.educomponent.questionbank.dao;

import com.huajie.educomponent.questionbank.entity.QuestionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by 10070 on 2017/7/21.
 */
@Repository
public interface QuestionTypeJpaRepo extends JpaRepository<QuestionTypeEntity, String>{

    @Query("from QuestionTypeEntity where deleted = 0 order by questionTypeCode")
    List<QuestionTypeEntity> list();
}
