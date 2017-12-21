package com.huajie.educomponent.course.service;

import com.huajie.appbase.BaseRetMessage;
import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.bo.CourseApproveBo;
import com.huajie.educomponent.course.bo.CourseApproveHisBo;
import com.huajie.educomponent.course.bo.CourseBasicBo;
import com.huajie.educomponent.course.constants.CourseApproveStatusType;
import com.huajie.educomponent.course.dao.CourseApproveHisJpaRepo;
import com.huajie.educomponent.course.dao.CourseBasicJpaRepo;
import com.huajie.educomponent.course.entity.CourseApproveHisEntity;
import com.huajie.educomponent.course.entity.CourseBasicEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 10070 on 2017/7/19.
 */
@Service
public class CourseApproveService {

    @Autowired
    private CourseBasicJpaRepo courseBasicJpaRepo;

    @Autowired
    private CourseApproveHisJpaRepo courseApproveHisJpaRepo;

    @Autowired
    private CourseService courseService;

    /**
     * 课程审批
     * @param userId
     * @param approveBo
     * @return
     * @throws Exception
     */
    public boolean approve(String userId, CourseApproveBo approveBo) {

        if (approveBo.getOperate()!=null && (approveBo.getOperate() < CourseApproveStatusType.APPROVING.getValue() || approveBo.getOperate() > CourseApproveStatusType.REFUSED.getValue())) {
            throw new BusinessException(BaseRetMessage.ARGUMENT_ERROR.getValue());
        }
        CourseBasicEntity entity = courseBasicJpaRepo.findById(approveBo.getCourseId());
        if (entity.getApproveStatus().equals(approveBo.getOperate())){
            throw new BusinessException(BaseRetMessage.REPEAT_OPERATION.getValue());
        }
        //更新课程状态，通过默认上架
        entity.setApproveStatus(approveBo.getOperate());
        if (approveBo.getOperate() == CourseApproveStatusType.APPROVED.getValue()) {
            entity.setIsOnShelves(true);
        }
        //驳回或通过，逻辑删除正在审批记录
        if (approveBo.getOperate() == CourseApproveStatusType.APPROVED.getValue() || approveBo.getOperate() == CourseApproveStatusType.REFUSED.getValue()){
            CourseApproveHisEntity approveHisEntity = courseApproveHisJpaRepo.findByCourseIdandOperate(approveBo.getCourseId(), CourseApproveStatusType.APPROVING.getValue());
            approveHisEntity.setDeleted(true);
            courseApproveHisJpaRepo.save(approveHisEntity);
        }
        courseBasicJpaRepo.save(entity);
        saveApproveHis(approveBo, userId);
        return true;
    }

    /**
     * 获取不同审批状态的课程列表
     * @param userId
     * @param approveStatus
     * @param page
     * @param size
     * @return
     */
    public PageResult<CourseBasicBo> getApproveStatus(String userId, Integer approveStatus, int page, int size) {
        PageResult<CourseBasicEntity> approveCourses;
        if (approveStatus == 2){
            approveCourses = courseBasicJpaRepo.findByStatus(approveStatus, page, size);
        }else {
            approveCourses = courseApproveHisJpaRepo.listPages(userId, approveStatus, page, size);
        }
        //包装返回数据
        PageResult<CourseBasicBo> result = new PageResult<CourseBasicBo>();
        List<CourseBasicBo> courseBriefBos = new ArrayList<CourseBasicBo>();
        for (CourseBasicEntity course :approveCourses.getItems()) {
            CourseBasicBo courseBriefBo = courseService.convertToBo(course);
            BeanUtils.copyProperties(course, courseBriefBo);
            courseBriefBos.add(courseBriefBo);
        }
        result.setItems(courseBriefBos);
        result.setTotal(approveCourses.getTotal());
        return result;
    }

    /**
     * 获取审批历史,用户和操作预留，可能会是查询条件
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public PageResult<CourseApproveHisBo> getApproveHis(String userId, Integer operate, int page, int size) {
        PageResult<CourseApproveHisEntity> approveCourses = courseApproveHisJpaRepo.list(userId, operate, page, size);
        //包装返回数据
        PageResult<CourseApproveHisBo> result = new PageResult<CourseApproveHisBo>();
        List<CourseApproveHisBo> courseBriefBos = new ArrayList<CourseApproveHisBo>();
        for (CourseApproveHisEntity course :approveCourses.getItems()) {
            CourseApproveHisBo courseApproveHisBo = new CourseApproveHisBo();
            BeanUtils.copyProperties(course, courseApproveHisBo);
            CourseBasicEntity courseBasicEntity = courseBasicJpaRepo.findOne(course.getCourseId());
            courseApproveHisBo.setCourseName(courseBasicEntity.getCourseName());
            courseApproveHisBo.setCourseApproveStatus(course.getOperate());
            courseBriefBos.add(courseApproveHisBo);
        }
        result.setItems(courseBriefBos);
        result.setTotal(approveCourses.getTotal());
        return result;
    }


    /**
     * 获取某课程审批情况
     * @param courseId
     * @return
     */
    public CourseApproveBo getApproveHis(String courseId){
        CourseApproveHisEntity approveHisEntity = courseApproveHisJpaRepo.findByCourseId(courseId);
        return convertToBo(approveHisEntity);
    }

    /**
     * 保存审批记录
     * @param approveBo
     * @param userId
     */
    public void saveApproveHis(CourseApproveBo approveBo, String userId){
        CourseApproveHisEntity hisEntity = new CourseApproveHisEntity();
        BeanUtils.copyProperties(approveBo, hisEntity);
        hisEntity.setDate(new Date());
        hisEntity.setApproverId(userId);
        courseApproveHisJpaRepo.save(hisEntity);
    }

    private CourseApproveBo convertToBo(CourseApproveHisEntity approveHisEntity){
        CourseApproveBo courseApproveBo = new CourseApproveBo();
        BeanUtils.copyProperties(approveHisEntity, courseApproveBo);
        return courseApproveBo;
    }

    /**
     * 提交审批
     * @param courseId
     * @return
     */
    public boolean submitApprove(String courseId) {
        CourseBasicEntity entity = courseBasicJpaRepo.findById(courseId);
        entity.setApproveStatus(CourseApproveStatusType.REQUEST.getValue());
        courseBasicJpaRepo.save(entity);
        return true;
    }

}
