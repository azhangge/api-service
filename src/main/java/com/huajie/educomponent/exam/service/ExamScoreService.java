package com.huajie.educomponent.exam.service;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.exam.bo.score.ExamScoreBo;
import com.huajie.educomponent.exam.bo.score.ExamUserScoreBo;
import com.huajie.educomponent.exam.bo.score.ScoreBriefBo;
import com.huajie.educomponent.exam.dao.ExamNoticeJpaRepo;
import com.huajie.educomponent.exam.dao.ExamUserPaperJpaRepo;
import com.huajie.educomponent.exam.entity.ExamNoticeEntity;
import com.huajie.educomponent.exam.entity.ExamUserPaperEntity;
import com.huajie.educomponent.usercenter.bo.UserBasicInfoBo;
import com.huajie.educomponent.usercenter.service.UserBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgz on 2017/9/14.
 */
@Service
public class ExamScoreService {

    @Autowired
    private ExamUserPaperJpaRepo examUserPaperJpaRepo;

    @Autowired
    private ExamNoticeJpaRepo examNoticeJpaRepo;

    @Autowired
    private UserBasicInfoService userBasicInfoService;

    /**
     * 用户查看考试成绩
     * @param userId
     * @return
     */
    public PageResult<ExamUserScoreBo> getUserScore(String userId, int pageIndex, int pageSize){
        PageResult<ExamUserPaperEntity> entityPageResult = examUserPaperJpaRepo.findByUserId(userId, pageIndex, pageSize);
        PageResult<ExamUserScoreBo> result = new PageResult<ExamUserScoreBo>();
        List<ExamUserScoreBo> scoreBos = new ArrayList<ExamUserScoreBo>();
        for (ExamUserPaperEntity examUserPaperEntity:entityPageResult.getItems()){
            ExamUserScoreBo examUserScoreBo = new ExamUserScoreBo();
            List<ScoreBriefBo> examScores = new ArrayList<ScoreBriefBo>();
            List<ExamUserPaperEntity> entities = examUserPaperJpaRepo.findByExamId(examUserPaperEntity.getExamId());
            for (ExamUserPaperEntity userPaperEntity:entities){
                setScoreBriefBo(userPaperEntity, examScores);
            }
            examUserScoreBo.setExamId(examUserPaperEntity.getExamId());
            ExamNoticeEntity examNoticeEntity = examNoticeJpaRepo.findOne(examUserPaperEntity.getExamId());
            examUserScoreBo.setExamName(examNoticeEntity.getExamName());
            examUserScoreBo.setExamScores(examScores);
            scoreBos.add(examUserScoreBo);
        }
        result.setTotal(entityPageResult.getTotal());
        result.setItems(scoreBos);
        return result;
    }

    /**
     * 管理员查看某场考试记录
     * @param examId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageResult<ExamScoreBo> getExamScore(String examId, int pageIndex, int pageSize){
        PageResult<ExamScoreBo> result = new PageResult<ExamScoreBo>();
        List<ExamScoreBo> examScoreBos = new ArrayList<ExamScoreBo>();
        PageResult<ExamUserPaperEntity> pageResult = examUserPaperJpaRepo.findByIns(examId, pageIndex, pageSize);
        for (ExamUserPaperEntity examUserPaperEntity:pageResult.getItems()){
            examScoreBos.add(convert(examUserPaperEntity));
        }
        result.setItems(examScoreBos);
        result.setTotal(pageResult.getTotal());
        return result;
    }

    /**
     * 成绩简要信息
     * @param userPaperEntity
     * @param examScores
     * @return
     */
    private List<ScoreBriefBo> setScoreBriefBo(ExamUserPaperEntity userPaperEntity, List<ScoreBriefBo> examScores){
        ScoreBriefBo bo = new ScoreBriefBo();
        if (userPaperEntity.getScore() != null) {
            bo.setUserScore(userPaperEntity.getScore());
        }
        if (userPaperEntity.getStartTime() != null && userPaperEntity.getSubmitTime() != null) {
            bo.setUserDuration((int) ((userPaperEntity.getSubmitTime().getTime() - userPaperEntity.getStartTime().getTime()) / 60000));
        }
        bo.setSubmitTime(userPaperEntity.getSubmitTime());
        examScores.add(bo);
        return examScores;
    }

    /**
     * toBo
     * @param examUserPaperEntity
     * @return
     */
    private ExamScoreBo convert(ExamUserPaperEntity examUserPaperEntity){
        ExamNoticeEntity exam = examNoticeJpaRepo.findOne(examUserPaperEntity.getExamId());
        UserBasicInfoBo user = userBasicInfoService.getUserBasicInfo(examUserPaperEntity.getUserId());
        ExamScoreBo examScoreBo = new ExamScoreBo();
        examScoreBo.setExamId(examUserPaperEntity.getExamId());
        if (examUserPaperEntity.getStartTime() != null && examUserPaperEntity.getSubmitTime() != null) {
            examScoreBo.setUserDuration((int) ((examUserPaperEntity.getSubmitTime().getTime() - examUserPaperEntity.getStartTime().getTime()) / 60000));
        }
        examScoreBo.setExamName(exam.getExamName());
        if (examUserPaperEntity.getScore() != null) {
            examScoreBo.setUserScore(examUserPaperEntity.getScore());
        }
        examScoreBo.setUserId(examUserPaperEntity.getUserId());
        if (!StringUtils.isEmpty(user.getNickName())) {
            examScoreBo.setUserName(user.getNickName());
        }
        if (examUserPaperEntity.getSubmitTime() != null){
            examScoreBo.setSubmitTime(examUserPaperEntity.getSubmitTime());
        }
        return examScoreBo;
    }

}
