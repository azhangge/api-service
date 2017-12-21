package com.huajie.educomponent.pubrefer.entity;

import com.huajie.appbase.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fangxing on 17-7-14.
 */
@Data
@Table(name = "file_storage")
@Entity
public class FileStorageEntity extends IdEntity {
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_size")
    private long fileSize;
    @Column(name = "suffix")
    private String suffix;
    @Column(name = "storage_group")
    private String storageGroup;
    @Column(name = "storage_path")
    private String storagePath;
    @Column(name = "owner_id")
    private String ownerId;
    @Column(name = "video_time_len")
    private Integer videoTimeLen;
    @Column(name = "transform_file_path")
    private String transformFilePath;
}
