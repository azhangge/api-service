package com.huajie.educomponent.exam.service;

import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.exam.bo.notice.ExamNoticeBo;
import com.huajie.educomponent.exam.bo.notice.ExamNoticeBriefBo;
import com.huajie.educomponent.exam.bo.notice.ExamNoticeBriefUserBo;
import com.huajie.educomponent.exam.bo.notice.ExamNoticeCreateBo;
import com.huajie.educomponent.exam.dao.ExamEnrollJpaRepo;
import com.huajie.educomponent.exam.dao.ExamNoticeJpaRepo;
import com.huajie.educomponent.exam.dao.ExamNoticeUserJpaRepo;
import com.huajie.educomponent.exam.dao.ExamPaperTemplateJpaRepo;
import com.huajie.educomponent.exam.entity.ExamEnrollEntity;
import com.huajie.educomponent.exam.entity.ExamNoticeEntity;
import com.huajie.educomponent.exam.entity.ExamNoticeUserEntity;
import com.huajie.educomponent.exam.entity.ExamPaperTemplateEntity;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.service.FileStorageService;
import com.huajie.educomponent.testpaper.dao.QuestionSetJpaRepo;
import com.huajie.educomponent.testpaper.entity.QuestionSetEntity;
import com.huajie.utils.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zgz on 2017/9/7.
 */
@Service
public class ExamNoticeService {

    @Autowired
    private ExamNoticeJpaRepo examNoticeJpaRepo;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ExamPaperTemplateJpaRepo examPaperTemplateJpaRepo;

    @Autowired
    private ExamNoticeUserJpaRepo examNoticeUserJpaRepo;

    @Autowired
    private ExamEnrollJpaRepo examEnrollJpaRepo;

    @Autowired
    private QuestionSetJpaRepo questionSetJpaRepo;

    /**
     * 创建/更新通知(考试)
     * @param noticeCreateBo
     * @return
     */
    // TODO: 2017/9/29 save返回entity 
    public ExamNoticeBo save(String userId, ExamNoticeCreateBo noticeCreateBo){
        ExamNoticeEntity examNoticeEntity = new ExamNoticeEntity();
        if (StringUtils.isEmpty(noticeCreateBo.getExamId())){
            examNoticeEntity.updateCreateInfo(userId);
        }else {
            examNoticeEntity = examNoticeJpaRepo.findOne(noticeCreateBo.getExamId());
            if (Boolean.TRUE.equals(examNoticeEntity.getIsPublish())){
                throw new BusinessException("已经发布，不允许修改");
            }
        }
        BeanUtils.copyProperties(noticeCreateBo, examNoticeEntity);
        examNoticeEntity.updateModifyInfo(userId);
        examNoticeEntity = examNoticeJpaRepo.save(examNoticeEntity);
        if (!StringUtils.isEmpty(noticeCreateBo.getQuestionSetId())){
            setNoticePaper(examNoticeEntity.getId(), noticeCreateBo.getQuestionSetId());
        }

        /**
         * 增加试卷与通知的关系
         */
        ExamPaperTemplateEntity examPaperTemplateEntity = new ExamPaperTemplateEntity();
        examPaperTemplateEntity.setExamId(examNoticeEntity.getId());
        examPaperTemplateEntity.setPaperId(noticeCreateBo.getQuestionSetId());
        examPaperTemplateJpaRepo.save(examPaperTemplateEntity);
        setNoticeToUser(noticeCreateBo);

        return convertToBo(examNoticeEntity);
    }

    /**
     * 发布考试通知
     * @param examId
     * @return
     */
    public void pushNotice(String userId, String examId){
        ExamNoticeEntity examNoticeEntity = examNoticeJpaRepo.findOne(examId);
        if (examNoticeEntity == null){
            throw new BusinessException("考试不存在");
        }
        examNoticeEntity.setIsPublish(Boolean.TRUE);
        examNoticeEntity.setPublishTime(new Date());
        examNoticeJpaRepo.save(examNoticeEntity);
    }

    /**
     * 用户获取通知（考试）列表
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageResult<ExamNoticeBriefUserBo> getUserExamNotices(String userId, int pageIndex, int pageSize){
        PageResult<ExamNoticeEntity> pageResult = examNoticeJpaRepo.getPublicAndUserExamNotices(userId, pageIndex, pageSize);
        PageResult<ExamNoticeBriefUserBo> result = new PageResult<ExamNoticeBriefUserBo>();
        List<ExamNoticeBriefUserBo> noticeBos = new ArrayList<ExamNoticeBriefUserBo>();
        for (ExamNoticeEntity entity:pageResult.getItems()){
            ExamNoticeBriefUserBo bo = convertToBriefUserBo(entity);
            ExamNoticeUserEntity examNoticeUser = examNoticeUserJpaRepo.findByUserIdAndExamId(userId, entity.getId());
            if(examNoticeUser != null) {
                bo.setReadStatus(examNoticeUser.getReadStatus());
            }
            noticeBos.add(bo);
        }
        result.setItems(noticeBos);
        result.setTotal(pageResult.getTotal());
        return result;
    }

    /**
     * 管理员查看通知（考试）
     * @param name
     * @param type
     * @param beginTime
     * @param endTime
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageResult<ExamNoticeBo> getExamNotices(String name, Integer type, Date beginTime, Date endTime, int pageIndex, int pageSize){

        PageResult<ExamNoticeEntity> pageResult = examNoticeJpaRepo.getExamNotices(name, type, beginTime, endTime, pageIndex, pageSize);
        PageResult<ExamNoticeBo> result = new PageResult<ExamNoticeBo>();
        List<ExamNoticeBo> noticeBos = new ArrayList<ExamNoticeBo>();
        for (ExamNoticeEntity entity:pageResult.getItems()){
            ExamNoticeBo bo = convertToBo(entity);
            noticeBos.add(bo);
        }
        result.setItems(noticeBos);
        result.setTotal(pageResult.getTotal());
        return result;
    }

    /**
     * 获取通知详情
     * @param examId
     * @return
     */
    public ExamNoticeBo getDetails(String userId, String examId){
        ExamNoticeEntity examNoticeEntity = examNoticeJpaRepo.findOne(examId);
        if (examNoticeEntity == null){
            throw new BusinessException("考试不存在");
        }
        setReadStatus(userId, examId);
        ExamNoticeBo result = convertToBo(examNoticeEntity);
        ExamEnrollEntity examEnroll = examEnrollJpaRepo.findByExamIdAndUserId(examId, userId);
        if (examEnroll != null){
            result.setUserEnrollStatus(1);
        }
        ExamPaperTemplateEntity examPaperTemplateEntity = examPaperTemplateJpaRepo.findByExamId(examId);
        if (examPaperTemplateEntity != null && examPaperTemplateEntity.getPaperId() != null) {
            String questionSetId = examPaperTemplateEntity.getPaperId();
            result.setQuestionSetId(questionSetId);
            QuestionSetEntity questionSetEntity = questionSetJpaRepo.findById(questionSetId);
            result.setPaperName(questionSetEntity.getName());
        }
        return result;
    }

    /**
     * 删除通知
     * @param examId
     */
    public void delete(String examId){
        ExamNoticeEntity examNoticeEntity = examNoticeJpaRepo.findOne(examId);
        if (examNoticeEntity == null){
            throw new BusinessException("考试不存在");
        }
        examNoticeEntity.setDeleted(Boolean.TRUE);
        examNoticeJpaRepo.save(examNoticeEntity);
    }

    /**
     * 设置该用户已读通知
     * @param userId
     * @param examId
     */
    private void setReadStatus(String userId, String examId){
        ExamNoticeUserEntity noticeUser = examNoticeUserJpaRepo.findByExamId(examId);
        //通知未设置对应的用户
        if (noticeUser == null){
            return;
        }
        ExamNoticeUserEntity noticeUserEntity = examNoticeUserJpaRepo.findByUserIdAndExamId(userId, examId);
        if (noticeUserEntity == null){
            throw new BusinessException("用户通知不存在");
        }
        //已读退出
        if(noticeUserEntity.getReadStatus() == 1){
            return;
        }
        noticeUserEntity.setReadStatus(1);
        examNoticeUserJpaRepo.save(noticeUserEntity);
    }

    /**
     * 设置对应模板
     * @param examId
     * @param questionSetId
     */
    private void setNoticePaper(String examId, String questionSetId){
        ExamPaperTemplateEntity templateEntity = new ExamPaperTemplateEntity();
        templateEntity.setExamId(examId);
        templateEntity.setPaperId(questionSetId);
        examPaperTemplateJpaRepo.save(templateEntity);
    }

    /**
     * 设置通知发给哪些用户
     * @param noticeCreateBo
     */
    private void setNoticeToUser(ExamNoticeCreateBo noticeCreateBo){
        if (noticeCreateBo.getUserIds() == null || noticeCreateBo.getUserIds().size() < 1){
            return;
        }
        for (String userId:noticeCreateBo.getUserIds()){
            ExamNoticeUserEntity noticeUserEntity = new ExamNoticeUserEntity();
            noticeUserEntity.setExamId(noticeCreateBo.getExamId());
            noticeUserEntity.setUserId(userId);
            noticeUserEntity.setReadStatus(0);
            examNoticeUserJpaRepo.save(noticeUserEntity);
        }
    }

    /**
     * toBo
     * @param entity
     * @return
     */
    private ExamNoticeBo convertToBo(ExamNoticeEntity entity){
        if (entity == null){
            return null;
        }
        ExamNoticeBo bo = new ExamNoticeBo();
        BeanUtils.copyProperties(entity, bo);
        bo.setExamId(entity.getId());
        if (!StringUtils.isEmpty(entity.getThumbnailId())){
            FileStorageBo file = fileStorageService.getStore(entity.getThumbnailId());
            bo.setThumbnailPath(PathUtils.getFilePath(file));
        }
        return bo;
    }

    /**
     * toBo
     * @param entity
     * @return
     */
    private ExamNoticeBriefUserBo convertToBriefUserBo(ExamNoticeEntity entity){
        if (entity == null){
            return null;
        }
        ExamNoticeBriefUserBo bo = new ExamNoticeBriefUserBo();
        BeanUtils.copyProperties(entity, bo);
        bo.setExamId(entity.getId());
        if (!StringUtils.isEmpty(entity.getThumbnailId())){
            FileStorageBo file = fileStorageService.getStore(entity.getThumbnailId());
            bo.setThumbnailPath(PathUtils.getFilePath(file));
        }
        return bo;
    }

}
