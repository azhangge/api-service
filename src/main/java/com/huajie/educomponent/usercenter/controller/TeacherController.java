package com.huajie.educomponent.usercenter.controller;

import com.github.pagehelper.util.StringUtil;
import com.huajie.appbase.*;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.usercenter.bo.*;
import com.huajie.educomponent.usercenter.service.TeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 10070 on 2017/8/11.
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value="detail",method= RequestMethod.GET)
    public ModelAndView teacherdetail(HttpServletRequest req) {
        String userId=(String)req.getSession().getAttribute("userId");
       // userId="ba53cf065e5ef9d5015e5eff77340006";
        TeacherBo teacherBo = null;
        try {
            teacherBo = teacherService.findDetail(userId, null);
        } catch (Exception e) {

        }
        return new ModelAndView("page/userteacherdetail", "teacherBo", teacherBo);

    }

    @ApiOperation("申请成为讲师（新建）、更新讲师")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseRetBo save(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                          @RequestBody TeacherBriefBo teacherBriefBo) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            TeacherCreateRetBo result = teacherService.save(userId, teacherBriefBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("查询讲师详情")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public BaseRetBo findOne(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                             @RequestParam(value = "teacherId", required = false) String teacherId) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            TeacherBo teacherBo = teacherService.findDetail(userId, teacherId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), teacherBo);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("添加/更新讲师履历")
    @RequestMapping(value = "/antecedent", method = RequestMethod.POST)
    public BaseRetBo saveTeacherAntecedent(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                           @RequestBody TeacherAntecedentBo teacherAntecedentBo) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            String antecedentId = teacherService.saveAntecedents(userId, teacherAntecedentBo);
            IdRetBo result = new IdRetBo(antecedentId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("删除讲师履历")
    @RequestMapping(value = "/antecedent", method = RequestMethod.DELETE)
    public BaseRetBo deleteTeacherAntecedent(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                             @RequestParam(value = "antecedentId", required = false)String antecedentId) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            teacherService.deleteAntecedents(userId, antecedentId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
    @ApiOperation("删除讲师履历")
    @RequestMapping(value = "/delAntecedent", method = RequestMethod.POST)
    public BaseRetBo deleteAntecedent(HttpServletRequest req,String antecedentId) {
        String userId=(String)req.getSession().getAttribute("userId");
        BaseRetBo retBo = new BaseRetBo();
        try {
            teacherService.deleteAntecedents(userId, antecedentId);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("讲师风采")
    @RequestMapping(value = "/appearance", method = RequestMethod.GET)
    public BaseRetBo appearance(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<TeacherBriefBo> result = teacherService.appearance(pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("管理员获取查看讲师列表")
    @RequestMapping(value = "/manage/list", method = RequestMethod.GET)
    public BaseRetBo teacherLIst(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                 @RequestParam(value = "approveStatus", required = false, defaultValue = "0") Integer approveStatus,
                                 @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            PageResult<TeacherBriefBo> result = teacherService.list(approveStatus, pageIndex, pageSize);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }

    @ApiOperation("管理员审批用户是否能成为讲师")
    @RequestMapping(value = "/manage/approve", method = RequestMethod.GET)
    public BaseRetBo teacherApprove(@CookieValue(value = "userId", defaultValue = EnvConstants.userId) String userId,
                                    @RequestBody TeacherApproveBo teacherApproveBo) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            teacherService.teacherApprove(userId, teacherApproveBo);
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue());
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        }
    }
}
