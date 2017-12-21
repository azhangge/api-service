package com.huajie.educomponent.exam.bo.enroll;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class ExamUserEnrollBo {

    private String userName;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enrollTime;
}
