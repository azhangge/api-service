package com.huajie.educomponent.portal.bo;

import lombok.Data;

/**
 * Created by zgz on 2017/8/24.
 */
@Data
public class AdPromptBo {

    private String adPromptId;
    private String name;
    private Integer adIndex;
    private String descriptions;
    private String resourceId;
    private int resourceType;
    private String resourceUrl;
    private String resourceName;
    private String webAdImgId;
    private String webAdImgPath;
    private String mobilAdImgId;
    private String mobilAdImgPath;
    private String bgColor;
    private Boolean isValid;
}
