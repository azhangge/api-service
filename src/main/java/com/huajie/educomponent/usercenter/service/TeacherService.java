package com.huajie.educomponent.usercenter.service;

import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.service.FileStorageService;
import com.huajie.educomponent.usercenter.bo.*;
import com.huajie.educomponent.usercenter.constans.AntecedentType;
import com.huajie.educomponent.usercenter.dao.*;
import com.huajie.educomponent.usercenter.entity.*;
import com.huajie.utils.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10070 on 2017/8/12.
 */
@Service
public class TeacherService {

    @Autowired
    private TeacherBriefJpaRepo teacherBriefJpaRepo;

    @Autowired
    private TeacherAntecedentsJpaRepo teacherAntecedentsJpaRepo;

    @Autowired
    private UserBasicInfoJpaRepo userBasicInfoJpaRepo;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserRealInfoJpaRepo userRealInfoJpaRepo;

    @Autowired
    private UserRealInfoService userRealInfoService;


    /**
     * 普通用户申请成为讲师、修改讲师基本信息
     *
     * @param teacherBriefBo
     * @return
     */
    public TeacherCreateRetBo save(String userId, TeacherBriefBo teacherBriefBo) {

        // TODO: 2017/10/9 先不要实名认证 
//        verify(userId);
        TeacherBriefEntity briefEntity = new TeacherBriefEntity();
        if (StringUtils.isEmpty(teacherBriefBo.getTeacherId())) {
            briefEntity.updateCreateInfo(userId);
            briefEntity.updateModifyInfo(userId);
            briefEntity.setApproveStatus(0);
            //todo 没有管理员 先申请就通过
            briefEntity.setApproveStatus(1);
        } else {
            briefEntity.updateModifyInfo(userId);
            briefEntity = teacherBriefJpaRepo.findOne(teacherBriefBo.getTeacherId());
        }
        TeacherCreateBo teacherCreateBo = new TeacherCreateBo();
        BeanUtils.copyProperties(teacherBriefBo, teacherCreateBo);
        BeanUtils.copyProperties(teacherCreateBo, briefEntity);
        briefEntity.setUserId(userId);
        TeacherBriefEntity result = teacherBriefJpaRepo.save(briefEntity);
        TeacherCreateRetBo teacherCreateRetBo = convertToTeacherCreateBo(result);
        teacherCreateRetBo.setUserId(userId);
        UserRealInfoBo userRealInfoBo = userRealInfoService.getUserRealInfo(userId);
        if (userRealInfoBo != null && !StringUtils.isEmpty(userRealInfoBo.getRealName())) {
            teacherCreateRetBo.setRealName(userRealInfoBo.getRealName());
            result.setUserRealInfoId(userRealInfoBo.getUserRealInfoId());
            teacherBriefJpaRepo.save(result);
        }
        return teacherCreateRetBo;
    }

    /**
     * 查询讲师详情
     *
     * @param userId
     * @param teacherId
     * @return
     */
    public TeacherBo findDetail(String userId, String teacherId) {

        TeacherBriefEntity teacherBrief;
        if (teacherId == null) {
            teacherBrief = teacherBriefJpaRepo.findByUserRealInfoId(userId);
        } else {
            teacherBrief = teacherBriefJpaRepo.findOne(teacherId);
        }
        if (teacherBrief == null) {
            throw new BusinessException("讲师信息不存在");
        }
        TeacherBo result = new TeacherBo();
        List<TeacherAntecedentsEntity> antecedents = teacherAntecedentsJpaRepo.findByTeacherBriefId(teacherBrief.getId());
        TeacherBriefBo teacherBriefBo = convertToTeacherBriefBo(teacherBrief);
        BeanUtils.copyProperties(teacherBriefBo, result);
        //基本信息
        UserBasicInfoEntity userBasic = userBasicInfoJpaRepo.findOne(userId);
        result.setGender(userBasic.getGender());
        //实名信息
        UserRealInfoEntity userRealInfo = null;
        if (!StringUtils.isEmpty(userBasic.getUserRealInfoId())) {
            userRealInfo = userRealInfoJpaRepo.findOne(userBasic.getUserRealInfoId());
            result.setUserRealInfoId(userRealInfo.getId());
        }
        if (userRealInfo != null && !StringUtils.isEmpty(userRealInfo.getRealName())) {
            result.setRealName(userRealInfo.getRealName());
        }
        if (userRealInfo != null && !StringUtils.isEmpty(userRealInfo.getIdNo())) {
            result.setIdentityCardId(userRealInfo.getIdNo());
        }
        //讲师各类型履历
        result.setEduList(getAntecedentList(antecedents, AntecedentType.EDU.getValue()));
        result.setWorkList(getAntecedentList(antecedents, AntecedentType.WORK.getValue()));
        result.setProjectList(getAntecedentList(antecedents, AntecedentType.PROJECT.getValue()));
        result.setTitleList(getAntecedentList(antecedents, AntecedentType.TITLE.getValue()));
        result.setAwardList(getAntecedentList(antecedents, AntecedentType.AWARD.getValue()));
        //图片path
        if (!StringUtils.isEmpty(teacherBrief.getIntroPicId())) {
            FileStorageBo intro = fileStorageService.getStore(teacherBrief.getIntroPicId());
            result.setFilePath(PathUtils.getFilePath(intro));
        }
        if (userRealInfo != null && !StringUtils.isEmpty(userRealInfo.getIdCardFrontImgId())) {
            FileStorageBo idCardFront = fileStorageService.getStore(userRealInfo.getIdCardFrontImgId());
            result.setIdCardFrontFilePath(PathUtils.getFilePath(idCardFront));
        }
        if (userRealInfo != null && !StringUtils.isEmpty(userRealInfo.getIdCardBackImgId())) {
            FileStorageBo idCardBack = fileStorageService.getStore(userRealInfo.getIdCardBackImgId());
            result.setIdCardBackFilePath(PathUtils.getFilePath(idCardBack));
        }
        return result;
    }

    /**
     * 添加、修改讲师履历
     *
     * @param teacherAntecedentBo
     * @return
     */
    public String saveAntecedents(String userId, TeacherAntecedentBo teacherAntecedentBo) {

        TeacherAntecedentsEntity antecedentsEntity = new TeacherAntecedentsEntity();
        if (StringUtils.isEmpty(teacherAntecedentBo.getAntecedentId())) {
            antecedentsEntity.updateCreateInfo(userId);
            antecedentsEntity.updateModifyInfo(userId);
        } else {
            antecedentsEntity.updateModifyInfo(userId);
            antecedentsEntity = teacherAntecedentsJpaRepo.findOne(teacherAntecedentBo.getAntecedentId());
        }
        BeanUtils.copyProperties(teacherAntecedentBo, antecedentsEntity);
        TeacherAntecedentsEntity result = teacherAntecedentsJpaRepo.save(antecedentsEntity);
        return result.getId();
    }

    /**
     * 删除讲师某履历
     *
     * @param antecedentId
     */
    public void deleteAntecedents(String userId, String antecedentId) {

        TeacherAntecedentsEntity antecedentsEntity = teacherAntecedentsJpaRepo.findById(antecedentId);
        if (antecedentsEntity == null) {
            throw new BusinessException("履历不存在");
        }
        TeacherBriefEntity briefEntity = teacherBriefJpaRepo.findOne(antecedentsEntity.getTeacherId());
        if (!briefEntity.getUserId().equals(userId)) {
            throw new BusinessException("无权限删除");
        }
        antecedentsEntity.setDeleted(true);
        teacherAntecedentsJpaRepo.save(antecedentsEntity);
    }

    /**
     * 讲师风采
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageResult<TeacherBriefBo> appearance(int pageIndex, int pageSize) {
        PageResult<TeacherBriefEntity> pageResult = teacherBriefJpaRepo.pageLists(1, pageIndex, pageSize);
        PageResult<TeacherBriefBo> result = new PageResult<TeacherBriefBo>();
        List<TeacherBriefBo> briefBos = new ArrayList<TeacherBriefBo>();
        for (TeacherBriefEntity teacherBriefEntity : pageResult.getItems()) {
            briefBos.add(convertToTeacherBriefBo(teacherBriefEntity));
        }
        result.setTotal(pageResult.getTotal());
        result.setItems(briefBos);
        return result;
    }

    /**
     * 讲师分页查询
     * @param approveStatus
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageResult<TeacherBriefBo> list(Integer approveStatus, int pageIndex, int pageSize){
        PageResult<TeacherBriefEntity> pageResult = teacherBriefJpaRepo.pageLists(approveStatus, pageIndex, pageSize);
        PageResult<TeacherBriefBo> result = new PageResult<TeacherBriefBo>();
        List<TeacherBriefBo> boList = new ArrayList<TeacherBriefBo>();
        for (TeacherBriefEntity teacherBriefEntity:pageResult.getItems()){
            TeacherBriefBo teacherBriefBo = new TeacherBriefBo();
            BeanUtils.copyProperties(teacherBriefEntity, teacherBriefBo);
            teacherBriefBo.setTeacherId(teacherBriefEntity.getId());
            boList.add(teacherBriefBo);
        }
        result.setItems(boList);
        result.setTotal(pageResult.getTotal());
        return result;
    }

    /**
     * 管理员审批讲师申请
     * @param userId
     * @param teacherApproveBo
     */
    public void teacherApprove(String userId, TeacherApproveBo teacherApproveBo){
        TeacherBriefEntity teacherBriefEntity = teacherBriefJpaRepo.findOne(teacherApproveBo.getTeacherId());
        if (teacherBriefEntity == null || teacherBriefEntity.getApproveStatus() != 0){
            throw new BusinessException("该用户未申请");
        }
        teacherBriefEntity.setApproveStatus(teacherApproveBo.getApproveType());
        teacherBriefJpaRepo.save(teacherBriefEntity);
    }
    /**
     * 根据用户获取对应的讲师信息
     *
     * @param userId
     * @return
     */
    public TeacherBriefBo getTeacherByUserId(String userId) {
        TeacherBriefEntity teacherBriefEntity = teacherBriefJpaRepo.findByUserRealInfoId(userId);
        return convertToTeacherBriefBo(teacherBriefEntity);
    }

    /**
     * 申请成为讲师前校验是否已经实名认证
     * @param userId
     */
    private void verify(String userId){
        UserBasicInfoEntity userBasicInfoEntity = userBasicInfoJpaRepo.findOne(userId);
        if (userBasicInfoEntity == null){
            throw new BusinessException("用户不存在");
        }
        UserRealInfoEntity userRealInfoEntity = userRealInfoJpaRepo.findOne(userBasicInfoEntity.getUserRealInfoId());
        if (userRealInfoEntity == null){
            throw new BusinessException("用户还未实名认证");
        }
        if (userRealInfoEntity.getApproveStatus() != 1){
            throw new BusinessException("实名认证未通过");
        }
    }

    public TeacherBriefBo getTeacherById(String teacherId) {
        TeacherBriefEntity teacherBriefEntity = teacherBriefJpaRepo.findOne(teacherId);
        return convertToTeacherBriefBo(teacherBriefEntity);
    }

    private List<TeacherAntecedentBo> getAntecedentList(List<TeacherAntecedentsEntity> antecedents, int type) {
        List<TeacherAntecedentBo> antecedentBos = new ArrayList<TeacherAntecedentBo>();
        for (TeacherAntecedentsEntity entity : antecedents) {
            if (entity.getType() == type) {
                TeacherAntecedentBo antecedent = convertToTeacherAntecedentBo(entity);
                antecedent.setTitleImgPath(getTitlePath(antecedent.getTitleImgId()));
                antecedentBos.add(antecedent);
            }
        }
        return antecedentBos;
    }

    private String getTitlePath(String fileId) {
        if (fileId != null) {
            FileStorageBo fileStorageBo = fileStorageService.getStore(fileId);
            return PathUtils.getFilePath(fileStorageBo);
        }
        return null;
    }

    private TeacherBriefBo convertToTeacherBriefBo(TeacherBriefEntity briefEntity) {
        if (briefEntity == null) {
            return null;
        }
        TeacherBriefBo teacherBriefBo = new TeacherBriefBo();
        BeanUtils.copyProperties(briefEntity, teacherBriefBo);
        teacherBriefBo.setTeacherId(briefEntity.getId());
        if(!StringUtils.isEmpty(briefEntity.getUserRealInfoId())){
            UserRealInfoEntity userRealInfo = userRealInfoJpaRepo.findOne(briefEntity.getUserRealInfoId());
            if (userRealInfo != null && !StringUtils.isEmpty(userRealInfo.getRealName())) {
                teacherBriefBo.setRealName(userRealInfo.getRealName());
            }
        }
        if (!StringUtils.isEmpty(briefEntity.getUserId())) {
            UserBasicInfoEntity userBasicInfo = userBasicInfoJpaRepo.findOne(briefEntity.getUserId());
            if (userBasicInfo.getGender() != null) {
                teacherBriefBo.setGender(userBasicInfo.getGender());
            }
        }
        return teacherBriefBo;
    }

    private TeacherBriefEntity convertToTeacherBriefEntity(TeacherBriefBo briefBo) {
        if (briefBo == null) {
            return null;
        }
        TeacherBriefEntity briefEntity = new TeacherBriefEntity();
        BeanUtils.copyProperties(briefBo, briefEntity);
        briefEntity.setId(briefBo.getTeacherId());
        return briefEntity;
    }

    private TeacherAntecedentBo convertToTeacherAntecedentBo(TeacherAntecedentsEntity antecedentsEntity) {
        if (antecedentsEntity == null) {
            return null;
        }
        TeacherAntecedentBo antecedentBo = new TeacherAntecedentBo();
        BeanUtils.copyProperties(antecedentsEntity, antecedentBo);
        antecedentBo.setAntecedentId(antecedentsEntity.getId());
        return antecedentBo;
    }

    private TeacherAntecedentsEntity convertToTeacherAntecedentsEntity(TeacherAntecedentBo briefBo) {
        if (briefBo == null) {
            return null;
        }
        TeacherAntecedentsEntity antecedentsEntity = new TeacherAntecedentsEntity();
        BeanUtils.copyProperties(briefBo, antecedentsEntity);
        antecedentsEntity.setId(briefBo.getAntecedentId());
        return antecedentsEntity;
    }

    private TeacherCreateRetBo convertToTeacherCreateBo(TeacherBriefEntity entity) {
        if (entity == null) {
            return null;
        }
        TeacherCreateRetBo bo = new TeacherCreateRetBo();
        BeanUtils.copyProperties(entity, bo);
        bo.setTeacherId(entity.getId());
        return bo;
    }

    //根据创建时间查询讲师信息
    public List<TeacherBriefBo> getTeacherBriefBos() {
        List<TeacherBriefEntity> teacherBriefEntities = teacherBriefJpaRepo.findAllByDeleted(false);
        List<TeacherBriefBo> teacherBriefBos = new ArrayList<TeacherBriefBo>();
        for (TeacherBriefEntity teacherBriefEntity : teacherBriefEntities) {
            TeacherBriefBo teacherBriefBo = convertToTeacherBriefBo(teacherBriefEntity);
            teacherBriefBos.add(teacherBriefBo);
        }
        return teacherBriefBos;
    }
}
