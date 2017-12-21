package com.huajie.educomponent.usercenter.service;

import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.service.FileStorageService;
import com.huajie.educomponent.usercenter.bo.UserRealApproveBo;
import com.huajie.educomponent.usercenter.bo.UserRealInfoBo;
import com.huajie.educomponent.usercenter.bo.UserRealInfoCreateBo;
import com.huajie.educomponent.usercenter.dao.UserBasicInfoJpaRepo;
import com.huajie.educomponent.usercenter.dao.UserRealInfoJpaRepo;
import com.huajie.educomponent.usercenter.entity.UserBasicInfoEntity;
import com.huajie.educomponent.usercenter.entity.UserRealInfoEntity;
import com.huajie.utils.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zgz on 2017/9/28.
 */
@Service
public class UserRealInfoService {

    @Autowired
    private UserBasicInfoJpaRepo userBasicInfoJpaRepo;

    @Autowired
    private UserRealInfoJpaRepo userRealInfoJpaRepo;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 申请实名认证
     * @param userRealInfoCreateBo
     * @return
     */
    public UserRealInfoBo requestAuth(String userId, UserRealInfoCreateBo userRealInfoCreateBo){

        UserBasicInfoEntity basicInfoEntity = userBasicInfoJpaRepo.findOne(userId);
        if (basicInfoEntity == null){
            throw new BusinessException("用户不存在");
        }
        if (!StringUtils.isEmpty(basicInfoEntity.getUserRealInfoId())){
            UserRealInfoEntity userRealInfoEntity = userRealInfoJpaRepo.findOne(basicInfoEntity.getUserRealInfoId());
            if (userRealInfoEntity != null && userRealInfoEntity.getApproveStatus() == 0){
                throw new BusinessException("已申请、正在审核中");
            }else if (userRealInfoEntity != null && userRealInfoEntity.getApproveStatus() == 1){
                throw new BusinessException("已通过，请勿重复申请");
            }
        }
        //创建实名信息、设置申请状态
        UserRealInfoEntity userRealInfoEntity = new UserRealInfoEntity();
        BeanUtils.copyProperties(userRealInfoCreateBo, userRealInfoEntity);
        userRealInfoEntity.setAuthenticationTime(new Date());
        userRealInfoEntity.setApproveStatus(0);
        // TODO: 2017/10/9
        userRealInfoEntity.setApproveStatus(1);
        userRealInfoEntity = userRealInfoJpaRepo.save(userRealInfoEntity);
        //将用户与实名绑定
        basicInfoEntity.setUserRealInfoId(userRealInfoEntity.getId());
        userBasicInfoJpaRepo.save(basicInfoEntity);
        UserRealInfoBo result = new UserRealInfoBo();
        BeanUtils.copyProperties(userRealInfoEntity, result);
        result.setUserRealInfoId(userRealInfoEntity.getId());
        return result;
    }

    /**
     * 获取用户实名信息
     * @param userId
     * @return
     */
    public UserRealInfoBo getUserRealInfo(String userId) {
        UserBasicInfoEntity userBasicInfoEntity = userBasicInfoJpaRepo.findOne(userId);
        if (userBasicInfoEntity == null){
            throw new BusinessException("用户不存在");
        }
        // TODO: 2017/10/9 先注释掉
//        if (StringUtils.isEmpty(userBasicInfoEntity.getUserRealInfoId())){
//            throw new BusinessException("用户还未申请实名认证");
//        }
        UserRealInfoEntity userRealInfoEntity = userRealInfoJpaRepo.findOne(userBasicInfoEntity.getUserRealInfoId());

//        if (userRealInfoEntity == null){
//            throw new BusinessException("用户还未实名认证");
//        }
        return convertToBo(userRealInfoEntity);
    }

    /**
     * 根据实名id获取实名
     */
    public UserRealInfoBo getRealById(String realInfoId){
        UserRealInfoEntity userRealInfoEntity = userRealInfoJpaRepo.findOne(realInfoId);
        return convertToBo(userRealInfoEntity);
    }

    /**
     * 管理员查看用户实名
     * @param realName
     * @param approveStatus
     * @param pageIndex
     * @param pageSize
     */
    public PageResult<UserRealInfoBo> userRealSearch(String realName, Integer approveStatus, int pageIndex, int pageSize){
        PageResult<UserRealInfoEntity> pageResult = userRealInfoJpaRepo.search(realName, approveStatus, pageIndex, pageSize);
        PageResult<UserRealInfoBo> result = new PageResult<UserRealInfoBo>();
        List<UserRealInfoBo> boList = new ArrayList<UserRealInfoBo>();
        for (UserRealInfoEntity userRealInfoEntity:pageResult.getItems()){
            UserRealInfoBo userRealInfoBo = new UserRealInfoBo();
            BeanUtils.copyProperties(userRealInfoEntity, userRealInfoBo);
            userRealInfoBo.setUserRealInfoId(userRealInfoEntity.getId());
            boList.add(userRealInfoBo);
        }
        result.setTotal(pageResult.getTotal());
        result.setItems(boList);
        return result;
    }

    /**
     * 管理员审批用户实名是否通过
     * @param userRealApproveBo
     */
    public void userRealApprove(UserRealApproveBo userRealApproveBo){
        if (userRealApproveBo.getApproveStatus() != 1 || userRealApproveBo.getApproveStatus() != 2){
            throw new BusinessException("参数错误");
        }
        UserRealInfoEntity userRealInfoEntity = userRealInfoJpaRepo.findOne(userRealApproveBo.getUserRealInfoId());
        if (userRealInfoEntity == null){
            throw new BusinessException("不存在该实名申请");
        }
        userRealInfoEntity.setApproveStatus(userRealApproveBo.getApproveStatus());
        userRealInfoJpaRepo.save(userRealInfoEntity);
    }

    /**
     * toBo
     * @param userRealInfoEntity
     * @return
     */
    private UserRealInfoBo convertToBo(UserRealInfoEntity userRealInfoEntity){
        if (userRealInfoEntity == null){
            return null;
        }
        UserRealInfoBo bo = new UserRealInfoBo();
        BeanUtils.copyProperties(userRealInfoEntity, bo);
        bo.setUserRealInfoId(userRealInfoEntity.getId());
        if (!StringUtils.isEmpty(userRealInfoEntity.getIdCardFrontImgId())) {
            FileStorageBo idCardFront = fileStorageService.getStore(userRealInfoEntity.getIdCardFrontImgId());
            bo.setIdCardFrontFilePath(PathUtils.getFilePath(idCardFront));
        }
        if (!StringUtils.isEmpty(userRealInfoEntity.getIdCardBackImgId())) {
            FileStorageBo idCardBack = fileStorageService.getStore(userRealInfoEntity.getIdCardBackImgId());
            bo.setIdCardBackFilePath(PathUtils.getFilePath(idCardBack));
        }
        return bo;
    }
}
