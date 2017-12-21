package com.huajie.educomponent.usercenter.service;

import com.huajie.appbase.BaseRetMessage;
import com.huajie.appbase.BusinessException;
import com.huajie.educomponent.log.LogService;
import com.huajie.educomponent.usercenter.bo.*;
import com.huajie.educomponent.usercenter.constans.AccountOpType;
import com.huajie.educomponent.usercenter.constans.AccountRoleType;
import com.huajie.educomponent.usercenter.constans.PasswordType;
import com.huajie.educomponent.usercenter.dao.*;
import com.huajie.educomponent.usercenter.entity.*;
import com.huajie.utils.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fangxing on 17-7-3.
 */
@Service
public class AccountService {

    @Autowired
    private AccountJpaRepo accountJpaRepo;

    @Autowired
    private CheckCodeService checkCodeService;

    @Autowired
    private UserBasicInfoService userBasicInfoService;

    @Autowired
    private SysMngService sysService;

    @Autowired
    private AccountTokenJpaRepo accountTokenJpaRepo;

    @Autowired
    private LogService logService;

    @Autowired
    private AccountRoleService accountRoleService;

    /**
     * 登录
     *
     * @param loginBo
     * @return
     * @throws Exception
     */
    public AccountReBo login(LoginBo loginBo) {

        AccountEntity account = accountJpaRepo.findByCellAndSysId(loginBo.getPhoneNo(), loginBo.getSysId());
        if (account == null) {
            throw new BusinessException(BaseRetMessage.USER_NOT_EXIST.getValue());
        }
        if (!((loginBo.getType() == PasswordType.STRING.getValue() && account.getPassword().equals(loginBo.getPassword()))
                || (loginBo.getType() == PasswordType.GESTURE.getValue() && account.getGesture().equals(loginBo.getGesture())))) {
            throw new BusinessException(BaseRetMessage.USER_NAME_PASSWORD_ERROR.getValue());
        }
        UserBasicInfoEntity userBasicInfo = userBasicInfoService.getUserBasicByCell(loginBo.getPhoneNo());
        cleanToken(loginBo.getPhoneNo(), loginBo.getDevice());
        AccountTokenEntity accountToken = saveAccountToken(account.getId(), loginBo.getDevice());
        logService.saveAccountLog(account.getId(), AccountOpType.LOGIN.getValue());
        AccountReBo accountReBo = new AccountReBo();
        List<SysInfoBo> userSysList = getSys(loginBo.getPhoneNo());
        accountReBo.setTargetSysId(account.getSysId());
        accountReBo.setUserSysList(userSysList);
        accountReBo.setToken(accountToken.getId());
        accountReBo.setUserId(userBasicInfo.getId());
        accountReBo.setNickName(userBasicInfo.getNickName());
        return accountReBo;
    }

    /**
     * 退出
     *
     * @param token
     * @return
     */
    public Boolean logout(String token) {
        if (token == null) {
            return true;
        }

        AccountTokenEntity accountToken = accountTokenJpaRepo.findOne(token);
        if (accountToken == null) {
            return true;
            //throw new RuntimeException(BaseRetMessage.ARGUMENT_ERROR.getValue());
        }
        accountTokenJpaRepo.delete(token);
        logService.saveAccountLog(accountToken.getAccountId(), AccountOpType.LOGOUT.getValue());
        return true;
    }

    /**
     * 注册
     *
     * @param bo
     * @return
     * @throws Exception
     */
    public AccountReBo register(RegAccountBo bo) {

        if (false == checkCodeService.verifyCheckCode(bo.getPhoneNo(), bo.getCode(), bo.getSysId())) {
            throw new BusinessException("验证码错误");
        }
        AccountEntity entityAccount = accountJpaRepo.findByCellAndSysId(bo.getPhoneNo(), bo.getSysId());
        if (bo.getType() == AccountOpType.REGISTER.getValue() && entityAccount != null) {
            throw new BusinessException("手机号已注册该系统");
        }
        if (bo.getType() == AccountOpType.FIND_PASSWORD.getValue() && entityAccount == null) {
            throw new BusinessException("没有此手机号的用户");
        }
        AccountReBo accountReBo = new AccountReBo();
        //新用户建立对应用户信息
        UserBasicInfoEntity userBasicInfo = saveUserInfo(bo.getPhoneNo());
        //保存账户信息
        AccountEntity entity = convertBoToEntity(bo);
        entity.setUserId(userBasicInfo.getId());
        entity.setUsername(bo.getPhoneNo());
        AccountEntity account = accountJpaRepo.save(entity);
        //设置用户角色
        accountRoleService.setUserRole(account.getUserId(), AccountRoleType.NORMAL.getValue());
        //保存注册记录
        logService.saveAccountLog(account.getId(), AccountOpType.REGISTER.getValue());
        //返回数据包装
        accountReBo.setUserId(userBasicInfo.getId());
        accountReBo.setTargetSysId(account.getSysId());
        accountReBo.setTargetSysId(bo.getSysId());
        accountReBo.setUserSysList(getSys(bo.getPhoneNo()));
        return accountReBo;
    }

    /**
     * web端根据账号注册
     *
     * @param bo
     * @return
     */
    public AccountReBo registerByAccount(RegAccountBo bo) {

        if(StringUtils.isEmpty(bo.getPassword())){
            throw new BusinessException("密码为必填项");
        }
        if(StringUtils.isEmpty(bo.getUsername())){
            throw new BusinessException("用户名为必填项");
        }
        AccountEntity accountEntity = accountJpaRepo.findByUsernameAndSysId(bo.getUsername(),bo.getSysId());
        if (bo.getType() == AccountOpType.REGISTER.getValue() && accountEntity != null) {
            throw new BusinessException("账号已注册该系统");
        }
        accountEntity = accountJpaRepo.findByCellAndSysId(bo.getPhoneNo(), bo.getSysId());
        if (bo.getType() == AccountOpType.REGISTER.getValue() && accountEntity != null) {
            throw new BusinessException("手机号已注册该系统");
        }
        if (false == checkCodeService.verifyCheckCode(bo.getPhoneNo(), bo.getCode(), bo.getSysId())) {
            throw new BusinessException("验证码错误");
        }

        AccountReBo accountReBo = new AccountReBo();
        //新用户建立对应用户信息
        UserBasicInfoEntity userBasicInfo = saveUserInfo(bo.getPhoneNo());
        //保存账户信息
        AccountEntity entity = convertBoToEntity(bo);
        entity.setUserId(userBasicInfo.getId());
        entity.setUsername(bo.getUsername());
        AccountEntity account = accountJpaRepo.save(entity);
        //设置用户角色
        accountRoleService.setUserRole(account.getUserId(), AccountRoleType.NORMAL.getValue());
        //保存注册记录
        logService.saveAccountLog(account.getId(), AccountOpType.REGISTER.getValue());
        //返回数据包装
        accountReBo.setUserId(userBasicInfo.getId());
        accountReBo.setTargetSysId(account.getSysId());
        accountReBo.setTargetSysId(bo.getSysId());
        accountReBo.setUserSysList(getSys(bo.getPhoneNo()));
        return accountReBo;
    }

    /**
     * web端根据账号登录
     *
     * @param loginBo
     * @return
     */
    public AccountReBo loginByAccount(LoginBo loginBo) {

//        AccountEntity account = accountJpaRepo.findByCellAndSysId(loginBo.getPhoneNo(), loginBo.getSysId());
        AccountEntity account = accountJpaRepo.findByUsernameAndSysId(loginBo.getUserName(), loginBo.getSysId());
        if (account == null) {
            throw new BusinessException(BaseRetMessage.USER_NOT_EXIST.getValue());
        }
        if (!((loginBo.getType() == PasswordType.STRING.getValue() && account.getPassword().equals(loginBo.getPassword()))
                || (loginBo.getType() == PasswordType.GESTURE.getValue() && account.getGesture().equals(loginBo.getGesture())))) {
            throw new BusinessException(BaseRetMessage.USER_NAME_PASSWORD_ERROR.getValue());
        }
        //UserBasicInfoEntity userBasicInfo = userBasicInfoService.getUserBasicByCell(loginBo.getPhoneNo());
        UserBasicInfoBo userBasicInfo = userBasicInfoService.getUserBasicInfo(account.getUserId());
        //cleanToken(loginBo.getUserName(), loginBo.getDevice());
        cleanTokenByUsername(loginBo.getUserName(), loginBo.getDevice());
        AccountTokenEntity accountToken = saveAccountToken(account.getId(), loginBo.getDevice());
        logService.saveAccountLog(account.getId(), AccountOpType.LOGIN.getValue());
        AccountReBo accountReBo = new AccountReBo();
        List<SysInfoBo> userSysList = getSysByUsername(loginBo.getUserName());
        accountReBo.setTargetSysId(account.getSysId());
        accountReBo.setUserSysList(userSysList);
        accountReBo.setPhoneNo(userBasicInfo.getPhoneNo());
        accountReBo.setToken(accountToken.getId());
        accountReBo.setUserId(userBasicInfo.getUserId());
        accountReBo.setNickName(userBasicInfo.getNickName());
        return accountReBo;
    }

    /**
     * 忘记密码
     *
     * @param bo
     * @return
     * @throws Exception
     */
    public AccountReBo remind(RegAccountBo bo) {

        if (false == checkCodeService.verifyCheckCode(bo.getPhoneNo(), bo.getCode(), bo.getSysId())) {
            throw new BusinessException("验证码错误");
        }
        AccountEntity entityAccount = accountJpaRepo.findByCellAndSysId(bo.getPhoneNo(), bo.getSysId());
        if (bo.getType() == AccountOpType.FIND_PASSWORD.getValue() && entityAccount == null) {
            throw new BusinessException("没有此手机号的用户");
        }
        AccountReBo accountReBo = new AccountReBo();
        //修改账户密码
        entityAccount.setPassword(bo.getPassword());
        entityAccount = accountJpaRepo.save(entityAccount);
        //删除旧token
        accountTokenJpaRepo.deleteByAccountId(entityAccount.getId());
        //保存操作记录
        logService.saveAccountLog(entityAccount.getId(), AccountOpType.FIND_PASSWORD.getValue());
        //返回数据包装
        UserBasicInfoEntity user = userBasicInfoService.getUserBasicByCell(bo.getPhoneNo());
        accountReBo.setUserId(user.getId());
        accountReBo.setTargetSysId(entityAccount.getSysId());
        accountReBo.setTargetSysId(bo.getSysId());
        accountReBo.setUserSysList(getSys(bo.getPhoneNo()));
        return accountReBo;
    }

    /**
     * 修改密码
     *
     * @param bo
     * @return
     */
    public AccountReBo changePassword(String userId, PasswordChangeBo bo) {

        AccountEntity accountEntity = accountJpaRepo.findByUserId(userId);
        if (!accountEntity.getPassword().equals(bo.getOldPassword())) {
            throw new BusinessException("原密码错误");
        }
        if (StringUtils.isEmpty(bo.getNewPassword())) {
            throw new RuntimeException("新密码不能为空");
        }
        accountEntity.setPassword(bo.getNewPassword());
        accountJpaRepo.save(accountEntity);
        accountTokenJpaRepo.deleteByAccountId(accountEntity.getId());
        //修改密码、全部token失效、需重新登录
        accountTokenJpaRepo.deleteByAccountId(accountEntity.getId());
        //包装数据返回
        AccountReBo accountReBo = new AccountReBo();
        accountReBo.setTargetSysId(bo.getSysId());
        accountReBo.setUserSysList(getSys(accountEntity.getPhoneNo()));
        UserBasicInfoEntity userBasicInfoEntity = userBasicInfoService.getUserBasicByCell(accountEntity.getPhoneNo());
        accountReBo.setUserId(userBasicInfoEntity.getId());
        return accountReBo;
    }

    /**
     * 设置手势密码
     * @param bo
     */
    public void setGesture(GesturePasswordBo bo) {
        AccountEntity entity = accountJpaRepo.findByCellAndSysId(bo.getPhoneNo(), bo.getSysId());
        if (entity == null){
            throw new BusinessException("账户不存在");
        }
        if (bo.getType() <1 || bo.getType() > 2){
            throw new BusinessException("参数错误");
        }
        if (bo.getType() == 2 && StringUtils.isEmpty(entity.getGesture())){
            throw new BusinessException("用户未设置手势密码");
        }
        if (bo.getType() == 1) {
            entity.setGesture(bo.getGesture());
            accountJpaRepo.save(entity);
        }
    }

    /**
     * 用户所有子系统
     *
     * @param cellphone
     * @return
     */
    private List<SysInfoBo> getSys(String cellphone) {
        List<SysInfoBo> sysInfoBoList = new ArrayList<SysInfoBo>();
        List<AccountEntity> accountEntities = accountJpaRepo.findByCellPhone(cellphone);
        for (AccountEntity accountEntity : accountEntities) {
            SysInfoBo sysInfoBo = sysService.getSys(accountEntity.getSysId());
            sysInfoBo.setSysId(sysInfoBo.getSysId());
            sysInfoBo.setSysName(sysInfoBo.getSysName());
            sysInfoBo.setSysRootPath(PathUtils.getSysPath(sysInfoBo));
            sysInfoBoList.add(sysInfoBo);
        }
        return sysInfoBoList;
    }

    /**
     * 根据token获取账户
     *
     * @param token
     * @return
     */
    public AccountBo getAccount(String token) {
        AccountTokenEntity accountToken = accountTokenJpaRepo.findOne(token);
        AccountEntity accountEntity = accountJpaRepo.findOne(accountToken.getAccountId());
        return convertToAccountBo(accountEntity);
    }

    /**
     * 保存token
     *
     * @param accountId
     * @return
     */
    private AccountTokenEntity saveAccountToken(String accountId, Integer device) {
        AccountTokenEntity accountToken = new AccountTokenEntity();
        accountToken.setLastAccessTime(new Date());
        accountToken.setAccountId(accountId);
        accountToken.setDevice(device);
        return accountTokenJpaRepo.save(accountToken);
    }

    /**
     * 注册账号时创建一个用户
     * @param phoneNo
     * @return
     */
    private UserBasicInfoEntity saveUserInfo(String phoneNo) {
        UserBasicInfoEntity userBasicInfoEntity = new UserBasicInfoEntity();
        userBasicInfoEntity.setPhoneNo(phoneNo);
        userBasicInfoEntity.setNickName(phoneNo);
        userBasicInfoEntity.setCreateDate(new Date());
        return userBasicInfoService.saveUserBasic(userBasicInfoEntity);
    }

    /**
     * 删除该账户对应终端的所有token
     * @param phoneNo
     */
    private void cleanToken(String phoneNo, Integer device){
        List<AccountEntity> accountEntities = accountJpaRepo.findByCellPhone(phoneNo);
        if (accountEntities.size() < 1){
            return;
        }
        List<AccountTokenEntity> accountTokenEntityList = accountTokenJpaRepo.findByAccountIdAndDevice(accountEntities.get(0).getId(), device);
        for (AccountTokenEntity accountTokenEntity:accountTokenEntityList){
            accountTokenEntity.setDeleted(true);
            accountTokenJpaRepo.save(accountTokenEntity);
        }
    }

    /**
     * 参数校验
     *
     * @param bo
     * @return
     */
    public Boolean verifyRegAccount(RegAccountBo bo) {
        if (StringUtils.isEmpty(bo.getPhoneNo())
                || StringUtils.isEmpty(bo.getPhoneNo())
                || StringUtils.isEmpty(bo.getSysId())) {
            return false;
        }
        return true;
    }

    /**
     * 参数校验
     *
     * @param bo
     * @return
     */
    public Boolean verifyLoginAccount(LoginBo bo) {

        if (StringUtils.isEmpty(bo.getPhoneNo())
                || (bo.getType() == PasswordType.STRING.getValue() && StringUtils.isEmpty(bo.getPassword()))
                || (bo.getType() == PasswordType.GESTURE.getValue() && StringUtils.isEmpty(bo.getGesture()))
                || StringUtils.isEmpty(bo.getSysId())) {
            return false;
        }
        if (bo.getDevice()>1 || bo.getDevice()<0){
            return false;
        }
        return true;
    }

    /**
     * 转换toEntitty
     *
     * @param bo
     * @return
     */
    private AccountEntity convertBoToEntity(RegAccountBo bo) {

        AccountEntity entity = new AccountEntity();
        BeanUtils.copyProperties(bo, entity);
        entity.setCreateTime(new Date());
        entity.setDeleted(false);
        return entity;
    }

    /**
     * 转换toBo
     *
     * @param accountEntity
     * @return
     */
    private AccountBo convertToAccountBo(AccountEntity accountEntity) {
        AccountBo accountBo = new AccountBo();
        BeanUtils.copyProperties(accountEntity, accountBo);
        return accountBo;
    }

    /**
     * 根据用户获得子系统
     *
     * @param username
     * @return
     */
    private List<SysInfoBo> getSysByUsername(String username) {
        List<SysInfoBo> sysInfoBoList = new ArrayList<SysInfoBo>();
        List<AccountEntity> accountEntities = accountJpaRepo.findByUsername(username);
        for (AccountEntity accountEntity : accountEntities) {
            SysInfoBo sysInfoBo = sysService.getSys(accountEntity.getSysId());
            sysInfoBo.setSysId(sysInfoBo.getSysId());
            sysInfoBo.setSysName(sysInfoBo.getSysName());
            sysInfoBo.setSysRootPath(PathUtils.getSysPath(sysInfoBo));
            sysInfoBoList.add(sysInfoBo);
        }
        return sysInfoBoList;
    }

    private void cleanTokenByUsername(String username, Integer device){
        List<AccountEntity> accountEntities = accountJpaRepo.findByUsername(username);
        if (accountEntities.size() < 1){
            return;
        }
        List<AccountTokenEntity> accountTokenEntityList = accountTokenJpaRepo.findByAccountIdAndDevice(accountEntities.get(0).getId(), device);
        for (AccountTokenEntity accountTokenEntity:accountTokenEntityList){
            accountTokenEntity.setDeleted(true);
            accountTokenJpaRepo.save(accountTokenEntity);
        }
    }

    /**
     * 参数校验（web端）
     *
     * @param bo
     * @return
     */
    public Boolean verifyLoginAccountForWeb(LoginBo bo) {
        if (StringUtils.isEmpty(bo.getUserName())
                || (bo.getType() == PasswordType.STRING.getValue() && StringUtils.isEmpty(bo.getPassword()))
                || StringUtils.isEmpty(bo.getSysId())) {
            return false;
        }
        if (bo.getDevice()>1 || bo.getDevice()<0){
            return false;
        }
        return true;
    }
}
