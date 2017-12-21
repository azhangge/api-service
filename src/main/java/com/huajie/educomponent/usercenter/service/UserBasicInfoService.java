package com.huajie.educomponent.usercenter.service;

import com.huajie.appbase.BusinessException;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.service.FileStorageService;
import com.huajie.educomponent.usercenter.bo.UserIconBo;
import com.huajie.educomponent.usercenter.bo.UserBasicInfoUpdateBo;
import com.huajie.educomponent.usercenter.bo.UserBasicInfoBo;
import com.huajie.educomponent.usercenter.bo.UserRoleBo;
import com.huajie.educomponent.usercenter.dao.AccountJpaRepo;
import com.huajie.educomponent.usercenter.dao.AccountRoleJpaRepo;
import com.huajie.educomponent.usercenter.dao.AccountTokenJpaRepo;
import com.huajie.educomponent.usercenter.dao.UserBasicInfoJpaRepo;
import com.huajie.educomponent.usercenter.entity.AccountEntity;
import com.huajie.educomponent.usercenter.entity.AccountRoleEntity;
import com.huajie.educomponent.usercenter.entity.AccountTokenEntity;
import com.huajie.educomponent.usercenter.entity.UserBasicInfoEntity;
import com.huajie.utils.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by fangxing on 17-7-5.
 */
@Service
public class UserBasicInfoService {

    @Autowired
    private UserBasicInfoJpaRepo userBasicInfoJpaRepo;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AccountRoleJpaRepo accountRoleJpaRepo;

    @Autowired
    private AccountJpaRepo accountJpaRepo;

    @Autowired
    private AccountTokenJpaRepo accountTokenJpaRepo;


    /**
     * 根据token获取userId
     *
     * @param token
     * @return
     */
    public String getUserByToken(String token) {

        return userBasicInfoJpaRepo.findUserInfoByToken(token);
    }

    /**
     * 根据手机号获取userId
     *
     * @param phoneNo
     * @return
     */
    public UserBasicInfoEntity getUserBasicByCell(String phoneNo) {

        return userBasicInfoJpaRepo.findByCellphone(phoneNo);
    }

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    public UserBasicInfoBo getUserBasicInfo(String userId) {
        UserBasicInfoEntity userRealInfo = userBasicInfoJpaRepo.findOne(userId);
        return convertToBo(userRealInfo);
    }

    /**
     * 修改用户信息,注册之后
     *
     * @param userBasicInfoUpdateBo
     * @return
     */
    public Boolean modifyUserBasicInfo(String userId, UserBasicInfoUpdateBo userBasicInfoUpdateBo) {

        UserBasicInfoEntity userRealInfo = userBasicInfoJpaRepo.findOne(userId);
        if (userRealInfo == null){
            throw new BusinessException("用户不存在");
        }
        BeanUtils.copyProperties(userBasicInfoUpdateBo, userRealInfo);
        userBasicInfoJpaRepo.save(userRealInfo);
        return true;
    }

    /**
     * 设置用户头像
     *
     * @param userIconBo
     * @return
     */
    public Boolean setUserBasicIcon(String userId, UserIconBo userIconBo) {

        UserBasicInfoEntity userRealInfo = userBasicInfoJpaRepo.findOne(userId);
        if (userRealInfo == null) {
            throw new BusinessException("用户不存在");
        }
        userRealInfo.setIcon(userIconBo.getFileId());
        userBasicInfoJpaRepo.save(userRealInfo);
        return true;
    }

    /**
     * 保存用户基本信息
     * @param entity
     * @return
     */
    public UserBasicInfoEntity saveUserBasic(UserBasicInfoEntity entity) {
        return userBasicInfoJpaRepo.save(entity);
    }

    /**
     * 用户角色
     * @param userId
     * @return
     */
    public UserRoleBo getUserRole(String userId){
        UserRoleBo result = new UserRoleBo();
        AccountRoleEntity accountRoleEntity = accountRoleJpaRepo.findByUserId(userId);
        if (accountRoleEntity == null){
            throw new BusinessException("用户不存在");
        }
        BeanUtils.copyProperties(accountRoleEntity, result);
        AccountEntity accountEntity = accountJpaRepo.findByUserId(userId);
        List<AccountTokenEntity> accountTokenEntityList = accountTokenJpaRepo.findByAccounts(accountEntity.getId());
        if (accountTokenEntityList.size() > 1 || accountTokenEntityList.size() <1){
            throw new BusinessException("无法获取token");
        }
        result.setToken(accountTokenEntityList.get(0).getId());
        return result;
    }

    /**
     * toBo
     * @param entity
     * @return
     */
    public UserBasicInfoBo convertToBo(UserBasicInfoEntity entity) {
        if (entity == null) {
            return null;
        }
        UserBasicInfoBo bo = new UserBasicInfoBo();
        BeanUtils.copyProperties(entity, bo);
        bo.setUserId(entity.getId());
        if (!StringUtils.isEmpty(entity.getIcon())) {
            FileStorageBo fileStorageBo = fileStorageService.getStore(entity.getIcon());
            bo.setFilePath(PathUtils.getFilePath(fileStorageBo));
        }
        return bo;
    }

}
