package com.huajie.educomponent.testpaper.bo;

import lombok.Data;
import java.util.List;

/**
 * Created by zgz on 2017/7/24.
 */
@Data
public class QuestionSetBo extends QuestionSetBriefBo {

    private List<QuestionSpeciesBo> species;
    private List<QuestionInBo> questions;
}
