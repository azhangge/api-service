package com.huajie.educomponent.exam.bo.test;

import lombok.Data;
import java.util.List;

/**
 * Created by zgz on 2017/9/20.
 */
@Data
public class TestQuestionSetBo {

    private String questionSetId;
    private String questionSetInsId;
    private String name;
    private Boolean isPublic;
    private Integer type;
    private String descriptions;
    private String tags;
    private Integer questionNum;
    private int isFavorite;//0未收藏  1已搜藏
    private List<TestQuestionSpeciesBo> species;
    private List<TestQuestionInBo> questions;
}
