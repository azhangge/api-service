package com.huajie.educomponent.testpaper.bo;

import lombok.Data;
import java.util.Date;

/**
 * Created by zgz on 2017/9/20.
 */
@Data
public class QuestionInstanceBo {

    private boolean deleted;
    private String creatorId;
    private Date createDate;
    private String modifierId;
    private Date modifyDate;
    private int active;
    private String name;
    private Integer type;
    private Boolean isPublic;
    private String descriptions;
    private String tags;
    private Integer questionNum;
}
