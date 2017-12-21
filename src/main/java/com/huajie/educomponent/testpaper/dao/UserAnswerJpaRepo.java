package com.huajie.educomponent.testpaper.dao;

import com.huajie.educomponent.testpaper.entity.UserAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Created by zgz on 2017/9/9.
 */
@Repository
public interface UserAnswerJpaRepo extends JpaRepository<UserAnswerEntity, String> {

    @Query("from UserAnswerEntity where userId=?1 and questionSetInsId=?2 and deleted=0")
    List<UserAnswerEntity> findByUserIdAndInstanceId(String userId, String insId);

    @Query("from UserAnswerEntity where questionSetInsId=?1 and questionId=?2 and deleted=0")
    UserAnswerEntity findByInstanceIdAndQuestionId(String insId, String questionId);

}
