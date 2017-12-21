package com.huajie.educomponent.portal.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.portal.entity.AdPromptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zgz on 2017/8/24.
 */
@Repository
public interface AdPromptJpaRepo extends JpaRepository<AdPromptEntity, String>{

    @Query("from AdPromptEntity where isValid =?1 and deleted = 0 order by adIndex")
    List<AdPromptEntity> listByValide(Boolean isValid);

    @Query("from AdPromptEntity where deleted = 0 order by adIndex")
    List<AdPromptEntity> list();

    PageResult<AdPromptEntity> findByCondition(Map<String,Object> condition, int pageIndex, int pageSize);
}
