package com.huajie.educomponent.testpaper.dao;

import com.huajie.educomponent.testpaper.entity.QuestionInEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by 10070 on 2017/7/25.
 */
@Repository
public interface QuestionInJpaRepo extends JpaRepository<QuestionInEntity, String> {

    @Query(value = "from QuestionInEntity where questionSetId=?1 and deleted=0 order by questionIndex asc")
    List<QuestionInEntity> findByQuestionSetId(String questionSetId);

    @Query(value = "from QuestionInEntity where questionSetId=?1 and questionId=?2")
    QuestionInEntity findByPaperIdAndQuestionId(String questionSetId, String questionId);

    @Query(value = "from QuestionInEntity where questionSetId=?1 and deleted=0")
    List<QuestionInEntity> findByQuestionSetInsId(String questionSetId);
}
