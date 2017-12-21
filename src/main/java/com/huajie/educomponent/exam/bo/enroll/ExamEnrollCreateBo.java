package com.huajie.educomponent.exam.bo.enroll;

import lombok.Data;

/**
 * Created by zgz on 2017/9/14.
 */
@Data
public class ExamEnrollCreateBo {

    private String examId;
    private Integer type; //1 报名;2 取消
}
