package com.huajie.educomponent.testpaper.bo;

import lombok.Data;
import java.util.List;

/**
 * Created by zgz on 2017/9/18.
 */
@Data
public class QuestionSetCreateBo {

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
    private int isFavorite;//0未收藏  1已搜藏

    private List<QuestionSpeciesBo> species;
    private List<QuestionInCreateBo> questions;
}
