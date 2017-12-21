package com.huajie.educomponent.testpaper.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.testpaper.entity.FavoriteQuestionSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteQuestionSetJapRepo  extends JpaRepository<FavoriteQuestionSetEntity, String> {

    PageResult<FavoriteQuestionSetEntity> listPages(String userId,  String search, Integer type, Integer orderBy, int pageIndex, int pageSize);

}
