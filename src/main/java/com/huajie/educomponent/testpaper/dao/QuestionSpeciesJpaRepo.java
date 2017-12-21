package com.huajie.educomponent.testpaper.dao;

import com.huajie.educomponent.testpaper.entity.QuestionSpeciesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by zgz on 2017/8/30.
 */
@Repository
public interface QuestionSpeciesJpaRepo extends JpaRepository<QuestionSpeciesEntity, String> {

    @Query("from QuestionSpeciesEntity where questionSetId=?1 and deleted=0 order by speciesIndex asc")
    List<QuestionSpeciesEntity> findByQuestionSetId(String questionSetId);
}
