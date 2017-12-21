package com.huajie.appbase;

/**
 * Created by Lenovo on 2017/8/15.
 */
public enum BaseRetMessage {
    SERVICE_ERROR("服务器异常"),

    FAILED("失败"),
    SUCCESS("成功"),
    NOT_FIND("资源不存在"),
    ARGUMENT_BLANK("参数不能为空"),
    ARGUMENT_ERROR("参数错误"),
    USER_NAME_PASSWORD_ERROR("用户名或密码错误"),
    LOGIN_SUCCESS("登录成功"),
    NO_RIGHT("无系统权限"),
    LOGOUT("注销成功"),
    USER_NOT_EXIST("用户不存在"),

    REPEAT_OPERATION("不能重复操作"),
    SET_USER_ICON("设置头像成功"),
    CHECK_CODE_ERROR("验证码错误"),
    CELL_REGISTERED("手机号已注册该系统"),

    TEACHER_NOT_FIND("讲师不存在"),
    COURSE_NOT_EXIST("课程不存在"),

    HAVED_COMMENT("您已经评价过该课程")
    ;


    private String value;

    BaseRetMessage(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }
}
