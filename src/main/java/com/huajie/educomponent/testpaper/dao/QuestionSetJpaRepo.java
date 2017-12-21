package com.huajie.educomponent.testpaper.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.testpaper.bo.QuestionSetBo;
import com.huajie.educomponent.testpaper.entity.QuestionSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 10070 on 2017/7/25.
 */
@Repository
public interface QuestionSetJpaRepo extends JpaRepository<QuestionSetEntity, String>{

    PageResult<QuestionSetEntity> listPages(String userId, Boolean isPublic, String search, Integer type, Boolean self, Integer orderBy, int pageIndex, int pageSize);

    @Query("from QuestionSetEntity where id=?1 and deleted=0")
    QuestionSetEntity findById(String questionSetId);
}
