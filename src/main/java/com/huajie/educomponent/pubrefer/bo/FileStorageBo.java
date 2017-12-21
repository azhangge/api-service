package com.huajie.educomponent.pubrefer.bo;

import lombok.Data;

/**
 * Created by Lenovo on 2017/8/16.
 */
@Data
public class FileStorageBo {

    private String fileId;
    private String fileName;
    private long fileSize;
    private String suffix;
    private String storagePath;
    private String storageGroup;
    private String transformFilePath;
    private Integer videoTimeLen;
}
