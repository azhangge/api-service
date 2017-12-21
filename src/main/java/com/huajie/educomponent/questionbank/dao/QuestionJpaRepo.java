package com.huajie.educomponent.questionbank.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.questionbank.bo.QuestionBriefBo;
import com.huajie.educomponent.questionbank.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 10070 on 2017/7/21.
 */
@Repository
public interface QuestionJpaRepo extends JpaRepository<QuestionEntity, String> {

    PageResult<QuestionEntity> listPages(String userId, String search, Integer type, Integer isPublic, String mainCategory, String subCategory, int pageIndex, int pageSize);

    @Query(value = "from QuestionEntity e where e.id in ?1")
    List<QuestionEntity> findByIds(List<String> ids);

    PageResult<QuestionEntity> userBanks(String userId, Integer action, String search, Integer type, String mainCategory, String subCategory, int pageIndex, int pageSize);

}
