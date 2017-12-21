package com.huajie.educomponent.testpaper.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.testpaper.entity.QuestionSetInsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zgz on 2017/9/9.
 */
@Repository
public interface QuestionSetInsJpaRepo extends JpaRepository<QuestionSetInsEntity, String> {

    PageResult<QuestionSetInsEntity> listBySetId(String userId, Integer type, int pageIndex, int pageSize);

}
