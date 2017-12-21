package com.huajie.educomponent.exam.service;

import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.exam.bo.enroll.ExamEnrollBo;
import com.huajie.educomponent.exam.bo.enroll.ExamEnrollCreateBo;
import com.huajie.educomponent.exam.bo.enroll.ExamUserEnrollBo;
import com.huajie.educomponent.exam.bo.enroll.ExamUserEnrollDetailBo;
import com.huajie.educomponent.exam.constants.ExamEnrollOperateType;
import com.huajie.educomponent.exam.dao.ExamEnrollJpaRepo;
import com.huajie.educomponent.exam.dao.ExamNoticeJpaRepo;
import com.huajie.educomponent.exam.entity.ExamEnrollEntity;
import com.huajie.educomponent.exam.entity.ExamNoticeEntity;
import com.huajie.educomponent.usercenter.bo.UserBasicInfoBo;
import com.huajie.educomponent.usercenter.service.UserBasicInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zgz on 2017/9/14.
 */
@Service
public class ExamEnrollService {

    @Autowired
    private ExamEnrollJpaRepo examEnrollJpaRepo;

    @Autowired
    private ExamNoticeJpaRepo examNoticeJpaRepo;

    @Autowired
    private UserBasicInfoService userBasicInfoService;

    /**
     * 报名、取消报名
     * @return
     */
    public Boolean enroll(String userId, ExamEnrollCreateBo enrollCreateBo){
        if (enrollCreateBo.getType() == ExamEnrollOperateType.ENROLL.getValue()){
            ExamEnrollEntity examEnroll = examEnrollJpaRepo.findByExamIdAndUserId(enrollCreateBo.getExamId(), userId);
            if (examEnroll != null){
                throw new BusinessException("已报名");
            }
            saveUserEnroll(userId, enrollCreateBo);
        }else if (enrollCreateBo.getType() == ExamEnrollOperateType.UN_ENROLL.getValue()){
            ExamEnrollEntity examEnroll = examEnrollJpaRepo.findByExamIdAndUserId(enrollCreateBo.getExamId(), userId);
            if (examEnroll == null){
                throw new BusinessException("还未报名，操作错误");
            }
            examEnroll.setDeleted(true);
            examEnrollJpaRepo.save(examEnroll);
            setExamEnrollNum(enrollCreateBo.getExamId(), -1);
        }else {
            throw new BusinessException("参数不正确");
        }
        return true;
    }

    /**
     * 查询某考试报名情况
     * @param examId
     * @return
     */
    public PageResult<ExamEnrollBo> search(String examId, String userId, int pageIndex, int pageSize){
        PageResult<ExamEnrollEntity> entityPageResult = examEnrollJpaRepo.search(examId, userId, pageIndex, pageSize);
        PageResult<ExamEnrollBo> result = new PageResult<ExamEnrollBo>();
        List<ExamEnrollBo> enrollBos = new ArrayList<ExamEnrollBo>();
        for (ExamEnrollEntity examEnrollEntity:entityPageResult.getItems()){
            ExamEnrollBo examEnrollBo = new ExamEnrollBo();
            BeanUtils.copyProperties(examEnrollEntity, examEnrollBo);
            ExamNoticeEntity exam =  examNoticeJpaRepo.findOne(examEnrollEntity.getExamId());
            BeanUtils.copyProperties(exam, examEnrollBo);
            UserBasicInfoBo user = userBasicInfoService.getUserBasicInfo(examEnrollEntity.getUserId());
            examEnrollBo.setUserName(user.getNickName());
            enrollBos.add(examEnrollBo);
        }
        result.setItems(enrollBos);
        result.setTotal(entityPageResult.getTotal());
        return result;
    }

    /**
     * 查询某考试报名情况 新
     * @param examId
     * @return
     */
    public ExamUserEnrollDetailBo getExamUserEnroll(String examId, String userId, int pageIndex, int pageSize){
        PageResult<ExamEnrollEntity> entityPageResult = examEnrollJpaRepo.search(examId, userId, pageIndex, pageSize);
        ExamUserEnrollDetailBo result = new ExamUserEnrollDetailBo();
        ExamNoticeEntity exam = examNoticeJpaRepo.findOne(examId);
        BeanUtils.copyProperties(exam, result);
        List<ExamUserEnrollBo> enrollBos = new ArrayList<ExamUserEnrollBo>();
        for (ExamEnrollEntity examEnrollEntity:entityPageResult.getItems()){
            ExamUserEnrollBo examUserEnrollBo = new ExamUserEnrollBo();
            examUserEnrollBo.setEnrollTime(examEnrollEntity.getDate());
            UserBasicInfoBo user = userBasicInfoService.getUserBasicInfo(examEnrollEntity.getUserId());
            examUserEnrollBo.setUserName(user.getNickName());
            enrollBos.add(examUserEnrollBo);
        }
        result.setExamUserEnrollBos(enrollBos);
        return result;
    }

    /**
     * 保存用户报名
     * @param userId
     * @param enrollCreateBo
     */
    private void saveUserEnroll(String userId, ExamEnrollCreateBo enrollCreateBo){
        ExamEnrollEntity examEnrollEntity = new ExamEnrollEntity();
        BeanUtils.copyProperties(enrollCreateBo, examEnrollEntity);
        examEnrollEntity.setDate(new Date());
        examEnrollEntity.setUserId(userId);
        examEnrollJpaRepo.save(examEnrollEntity);
        setExamEnrollNum(enrollCreateBo.getExamId(), 1);
    }

    private void setExamEnrollNum(String examId, int enrollType){
        ExamNoticeEntity examNoticeEntity = examNoticeJpaRepo.findOne(examId);
        examNoticeEntity.setTotalJoinNum(examNoticeEntity.getTotalJoinNum() + enrollType);
        examNoticeJpaRepo.save(examNoticeEntity);
    }
}
