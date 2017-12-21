package com.huajie.educomponent.testpaper.service;

import com.huajie.appbase.BusinessException;
import com.huajie.educomponent.testpaper.dao.QuestionInJpaRepo;
import com.huajie.educomponent.testpaper.entity.QuestionInEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zgz on 2017/9/23.
 */
@Service
public class QuestionInService {

    @Autowired
    private QuestionInJpaRepo questionInJpaRepo;

    public float getPaperQuestionScore(String questionSetId, String questionId){
        QuestionInEntity questionIn = questionInJpaRepo.findByPaperIdAndQuestionId(questionSetId, questionId);
        if (questionIn == null || ((questionIn.getScore() - 0.00001f > 0))) {
            throw new BusinessException("未设置分数");
        }
        return questionIn.getScore();
    }
}
