package com.huajie.educomponent.usercenter.service;

import com.huajie.educomponent.usercenter.bo.SysInfoBo;
import com.huajie.educomponent.usercenter.bo.SysRegRequestBo;
import com.huajie.educomponent.usercenter.bo.SysRegResultBo;
import com.huajie.educomponent.usercenter.dao.SysInfoJpaRepo;
import com.huajie.educomponent.usercenter.entity.SysInfoEntity;
import com.huajie.utils.SysRegRetCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fangxing on 17-7-3.
 */
@Service
public class SysMngService {

    @Autowired
    private SysInfoJpaRepo sysInfoEntityJpaRepo;

    @Autowired
    private AccountService accountService;

    /**
     * 获取子系统
     * @param sysId
     * @return
     */
    public SysInfoBo getSys(String sysId){
        SysInfoEntity sysInfoEntity = sysInfoEntityJpaRepo.findOne(sysId);
        return convertToBo(sysInfoEntity);
    }

    public SysRegResultBo regSys(SysRegRequestBo regRequestBo) {
        SysRegResultBo resultBo = new SysRegResultBo();
        String sysCode = register(regRequestBo);
        if (sysCode == null) {
            resultBo.setRetCode(SysRegRetCode.FAIL);
        } else {
            resultBo.setRetCode(SysRegRetCode.SUCCESS);
            resultBo.setSysId(sysCode);
        }

        return resultBo;
    }

    public Boolean isSysExist(String sysId) {
        SysInfoEntity entity = sysInfoEntityJpaRepo.findOne(sysId);
        if (entity != null || entity.isDeleted() == false) {
            return true;
        }

        return false;
    }

    public Boolean isSysExist(String host, int port) {
        SysInfoEntity entity = sysInfoEntityJpaRepo.find(host, port);
        if (entity != null) {
            return true;
        }

        return false;
    }

    private String register(SysRegRequestBo requestBo) {
        if (isSysExist(requestBo.getHost(), requestBo.getPort())){
            return null;
        }

        SysInfoEntity entity = new SysInfoEntity();
        entity.setSysHost(requestBo.getHost());
        entity.setSysPort(requestBo.getPort());
        entity.setSysName(requestBo.getName());
        entity.setDeleted(false);

        sysInfoEntityJpaRepo.save(entity);
        return entity.getId();
    }

    private SysInfoBo convertToBo(SysInfoEntity entity){
        SysInfoBo sysInfoBo = new SysInfoBo();
        BeanUtils.copyProperties(entity, sysInfoBo);
        sysInfoBo.setSysId(entity.getId());
        return sysInfoBo;
    }
}
