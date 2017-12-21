package com.huajie.educomponent.usercenter.dao;

import com.huajie.educomponent.usercenter.entity.UserBasicInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fangxing on 17-7-5.
 */
@Repository
public interface UserBasicInfoJpaRepo extends JpaRepository<UserBasicInfoEntity, String> {

    @Query("from UserBasicInfoEntity  e where e.phoneNo=?1 and e.deleted=0")
    UserBasicInfoEntity findByCellphone(String cellphone);

    @Query("from UserBasicInfoEntity  e where  e.deleted=0")
    List<UserBasicInfoEntity> findAll();

    String findUserInfoByToken(String token);
}
