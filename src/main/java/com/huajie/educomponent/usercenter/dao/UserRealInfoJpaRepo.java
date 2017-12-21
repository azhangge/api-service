package com.huajie.educomponent.usercenter.dao;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.usercenter.entity.UserRealInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zgz on 2017/9/28.
 */
@Repository
public interface UserRealInfoJpaRepo extends JpaRepository<UserRealInfoEntity, String>{

    PageResult<UserRealInfoEntity> search(String realName, Integer approveStatus, int pageIndex, int pageSize);

}
