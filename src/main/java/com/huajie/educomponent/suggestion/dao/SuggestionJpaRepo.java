package com.huajie.educomponent.suggestion.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.suggestion.entity.SuggestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zgz on 2017/9/6.
 */
@Repository
public interface SuggestionJpaRepo extends JpaRepository<SuggestionEntity, String>{

    PageResult<SuggestionEntity> listPages(String userId, String keyword, Integer type, int pageIndex, int pageSize);
}
