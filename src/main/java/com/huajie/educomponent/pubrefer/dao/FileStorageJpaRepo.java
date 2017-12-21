package com.huajie.educomponent.pubrefer.dao;


import com.huajie.educomponent.pubrefer.entity.FileStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by fangxing on 17-7-3.
 */
@Repository
public interface FileStorageJpaRepo extends JpaRepository<FileStorageEntity, String> {
    @Query("select e from FileStorageEntity e where e.id=?1 and e.deleted=0")
    FileStorageEntity findById(String id);

    @Query("select e from FileStorageEntity e where e.ownerId=null and e.deleted=0")
    List<FileStorageEntity> findIdleFiles();

    @Query("select e from FileStorageEntity e where e.ownerId=?1 and e.deleted=0")
    List<FileStorageEntity> findByOwnerId(String ownerId);
}
