package com.huajie.educomponent.course.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * Created by zgz on 2017/8/18.
 */
@Data
public class CourseApproveHisBo {

    private String courseId;
    private String courseName;
    private String approverId;
    private int courseApproveStatus;
    private String reason;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
}
