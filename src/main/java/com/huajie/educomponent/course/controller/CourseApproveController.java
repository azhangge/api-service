package com.huajie.educomponent.course.controller;

import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.course.bo.CourseApproveBo;
import com.huajie.educomponent.course.bo.CourseApproveHisBo;
import com.huajie.educomponent.course.bo.CourseBasicBo;
import com.huajie.educomponent.course.service.CourseApproveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zgz on 2017/7/19.
 */
@Api("课程管理")
@RestController
@RequestMapping("/course")
public class CourseApproveController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CourseApproveService courseApproveService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ApiOperation("课程审批")
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public BaseRetBo approve(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                             @RequestBody CourseApproveBo approveBo) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            boolean result = courseApproveService.approve(userId, approveBo);
            if (result == true) {
                return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
            } else {
                return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.FAILED.getValue());
            }
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("管理员查看审批状态的课程")
    @RequestMapping(value = "/approve", method = RequestMethod.GET)
    public BaseRetBo search(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                            @RequestParam(value = "approveStatus", required = false) Integer approveStatus,
                            @RequestParam(value = "pageIndex", defaultValue = "0", required = false) int page,
                            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int size) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<CourseBasicBo> result = courseApproveService.getApproveStatus(userId, approveStatus, page, size);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("管理员查看审批日志")
    @RequestMapping(value = "/approve/his", method = RequestMethod.GET)
    public BaseRetBo searchHis(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                               @RequestParam(value = "operate", required = false) Integer operate,
                               @RequestParam(value = "pageIndex", defaultValue = "0", required = false) int page,
                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) int size) {

        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<CourseApproveHisBo> result = courseApproveService.getApproveHis(userId, operate, page, size);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }


    @RequestMapping(value = "redistest", method = RequestMethod.GET)
    public String testRedis() {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        return stringRedisTemplate.opsForValue().get("aaa");
    }

    @RequestMapping(value = "mqtest", method = RequestMethod.GET)
    public String testMq(@RequestParam String message) {
        rabbitTemplate.convertAndSend("my-queue", message);
        return "success";
    }
}
