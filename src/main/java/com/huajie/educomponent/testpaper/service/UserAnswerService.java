package com.huajie.educomponent.testpaper.service;

import com.huajie.educomponent.testpaper.bo.ExamUserAnswerRetBo;
import com.huajie.educomponent.testpaper.dao.QuestionSetInsJpaRepo;
import com.huajie.educomponent.testpaper.dao.QuestionSetJpaRepo;
import com.huajie.educomponent.testpaper.dao.UserAnswerJpaRepo;
import com.huajie.educomponent.testpaper.entity.QuestionSetEntity;
import com.huajie.educomponent.testpaper.entity.QuestionSetInsEntity;
import com.huajie.educomponent.testpaper.entity.UserAnswerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by zgz on 2017/9/23.
 */
@Service
public class UserAnswerService {

    @Autowired
    private UserAnswerJpaRepo userAnswerJpaRepo;

    @Autowired
    private QuestionSetJpaRepo questionSetJpaRepo;

    @Autowired
    private QuestionSetInsJpaRepo questionSetInsJpaRepo;

    /**
     * 获取用户考试总分和是否及格
     * @param questionSetInsId
     * @return
     */
    public ExamUserAnswerRetBo getExamTotalScore(String userId, String questionSetInsId){
        ExamUserAnswerRetBo examUserAnswerRetBo = new ExamUserAnswerRetBo();
        float score = 0;
        boolean isPass = Boolean.FALSE;
        List<UserAnswerEntity> userAnswers = userAnswerJpaRepo.findByUserIdAndInstanceId(userId, questionSetInsId);
        for (UserAnswerEntity userAnswerEntity:userAnswers){
            score = score + userAnswerEntity.getReal_score();
        }
        QuestionSetInsEntity questionSetIns = questionSetInsJpaRepo.findOne(questionSetInsId);
        QuestionSetEntity questionSet = questionSetJpaRepo.findById(questionSetIns.getQuestionSetId());
        if (score >= questionSet.getTotalScore()){//修改
            isPass = Boolean.TRUE;
        }
        examUserAnswerRetBo.setIsPass(isPass);
        examUserAnswerRetBo.setScore(score);
        return examUserAnswerRetBo;
    }
}
