package com.huajie.educomponent.usercenter.service;

import com.huajie.appbase.BusinessException;
import com.huajie.educomponent.usercenter.bo.PhoneCodeBo;
import com.huajie.educomponent.usercenter.dao.CheckCodeJpaRepo;
import com.huajie.educomponent.usercenter.entity.CheckCodeEntity;
import com.huajie.utils.SendMessageUtiles;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by fangxing on 17-7-5.
 */
@Service
public class CheckCodeService {

    @Autowired
    private CheckCodeJpaRepo checkCodeJpaRepo;

    /**
     * 发送手机验证码
     * @param phoneNo
     * @param sysId
     * @param type
     * @param device
     * @return
     * @throws Exception
     */
    public PhoneCodeBo sendCode(String phoneNo, String sysId, Integer type, Integer device) {
        PhoneCodeBo phoneCodeBo = new PhoneCodeBo();
        String code = generateCode();
        Map<String, Object> result = SendMessageUtiles.sendMessage("156639", phoneNo, code);
        if ("000000".equals(result.get("statusCode"))) {
            CheckCodeEntity checkCodeEntity = new CheckCodeEntity();
            checkCodeEntity.setPhoneNo(phoneNo);
            checkCodeEntity.setCode(code.toString());
            checkCodeEntity.setExpiryDate(new Date(new Date().getTime() + 300000));
            checkCodeEntity.setSysId(sysId);
            CheckCodeEntity checkCode = checkCodeJpaRepo.save(checkCodeEntity);
            phoneCodeBo.setExpiryDate(checkCode.getExpiryDate());
            phoneCodeBo.setCode(checkCode.getCode());
            phoneCodeBo.setPhoneNo(phoneNo);
            phoneCodeBo.setSysId(sysId);
        }else {
            throw new BusinessException("错误信息= " + result.get("statusMsg"));
        }
        return phoneCodeBo;
    }

    /**
     * 校验手机验证码
     * @param cellphone
     * @param code
     * @param sysId
     * @return
     * @throws Exception
     */
    public Boolean verifyCheckCode(String cellphone, String code, String sysId) {
        CheckCodeEntity entity = checkCodeJpaRepo.find(cellphone, code, sysId);
        //验证码已被使用
        if (entity == null){
            throw new BusinessException("验证码不存在或已经被使用");
        }
        //验证码已过期
        if (entity.getExpiryDate().getTime() < System.currentTimeMillis()){
            throw new BusinessException("验证码已过期");
        }
        entity.setDeleted(true);
        checkCodeJpaRepo.save(entity);
        return true;
    }

    /**
     * 生成手机验证码
     * @return
     */
    private String generateCode(){
        StringBuffer code = new StringBuffer();
        Random random = new Random();
        for (int i = 0;i < 4;i++){
            code.append(random.nextInt(9));
        }
        return code.toString();
    }
}
