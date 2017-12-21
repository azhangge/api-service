package com.huajie.educomponent.testpaper.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Created by zgz on 2017/9/4.
 */
@Data
public class QuestionSetBriefBo {

    private String questionSetId;
    private String questionSetInsId;
    private String name;
    private Boolean isPublic;
    private Integer type;//1 题集  2 试卷
    private String descriptions;
    private String tags;
    private Integer questionNum;
    private Float totalScore;
    private Integer strategy;//1固定 2随机
    private String creatorName;//题集试卷创建者名称
    private int isFavorite;//0未收藏  1已搜藏
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createDate;
    private int accessCount;//试卷被浏览总数
}
