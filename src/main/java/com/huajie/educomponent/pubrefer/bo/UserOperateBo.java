package com.huajie.educomponent.pubrefer.bo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

/**
 * Created by fangxing on 17-7-11.
 */
@Api("用户操作")
@Data
public class UserOperateBo {

    @ApiParam("课程标识")
    private List<String> objectId;
    @ApiParam("1收藏 2购买 3学习 4取消收藏 5退出学习 6最近访问 7题目收藏")
    private Integer type;

}
