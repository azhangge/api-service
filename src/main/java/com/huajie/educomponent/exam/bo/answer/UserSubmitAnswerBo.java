package com.huajie.educomponent.exam.bo.answer;

import lombok.Data;
import java.util.List;

/**
 * Created by zgz on 2017/9/19.
 */
@Data
public class UserSubmitAnswerBo {
    private String questionSetId;
    private String questionSetInsId;
    private Integer type;//1 练习， 2考试
    private List<UserAnswerBo> answers;
}
