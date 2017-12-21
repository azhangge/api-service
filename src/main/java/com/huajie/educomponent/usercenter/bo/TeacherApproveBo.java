package com.huajie.educomponent.usercenter.bo;

import lombok.Data;

/**
 * Created by zgz on 2017/9/30.
 */
@Data
public class TeacherApproveBo {

    private String teacherId;
    private Integer approveType;//1通过  2不通过
}
