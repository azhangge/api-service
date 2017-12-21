package com.huajie.educomponent.exam.bo.enroll;

import com.huajie.educomponent.exam.bo.notice.ExamNoticeBriefBo;
import lombok.Data;

import java.util.List;

@Data
public class ExamUserEnrollDetailBo extends ExamNoticeBriefBo {

    private Integer totalJoinNum;
    private List<ExamUserEnrollBo> examUserEnrollBos;
}
