package com.huajie.educomponent.testpaper.service;

import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.testpaper.bo.QuestionSetInsBo;
import com.huajie.educomponent.testpaper.dao.QuestionSetInsJpaRepo;
import com.huajie.educomponent.testpaper.dao.QuestionSetJpaRepo;
import com.huajie.educomponent.testpaper.entity.QuestionSetEntity;
import com.huajie.educomponent.testpaper.entity.QuestionSetInsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zgz on 2017/9/9.
 */
@Service
public class QuestionSetInsService {

    @Autowired
    private QuestionSetInsJpaRepo questionSetInsJpaRepo;


    /**
     * 创建实例
     * @param userId
     * @param questionSetId
     * @return
     * @throws Exception
     */
    public QuestionSetInsBo save(String userId, String questionSetId, Integer type) {
        QuestionSetInsEntity questionSetInsEntity = new QuestionSetInsEntity();
        questionSetInsEntity.setQuestionSetId(questionSetId);
        questionSetInsEntity.setUserId(userId);
        questionSetInsEntity.setCreateTime(new Date());
        questionSetInsEntity.setType(type);
        questionSetInsEntity = questionSetInsJpaRepo.save(questionSetInsEntity);
        return convertToBo(questionSetInsEntity);
    }

    /**
     * 查询
     * @param insId
     * @return
     * @throws Exception
     */
    public QuestionSetInsBo get(String insId) {
        QuestionSetInsEntity questionSetInsEntity = questionSetInsJpaRepo.findOne(insId);
        if (questionSetInsEntity == null){
            throw new BusinessException("不存在");
        }
        return convertToBo(questionSetInsEntity);
    }

    /**
     * 按照类型获取全部作答
     * @return
     */
    public PageResult<QuestionSetInsBo> getInsById(String userId, Integer type, int pageIndex, int pageSize) {
        PageResult<QuestionSetInsEntity> questionSetInsList = questionSetInsJpaRepo.listBySetId(userId, type, pageIndex, pageSize);
        List<QuestionSetInsBo> list = new ArrayList<QuestionSetInsBo>();
        for (QuestionSetInsEntity entity:questionSetInsList.getItems()){
            list.add(convertToBo(entity));
        }
        PageResult<QuestionSetInsBo> result = new PageResult<QuestionSetInsBo>();
        result.setTotal(questionSetInsList.getTotal());
        result.setItems(list);
        return result;
    }

    /**
     * toBo
     * @param entity
     * @return
     */
    public QuestionSetInsBo convertToBo(QuestionSetInsEntity entity){
        if (entity == null){
            return null;
        }
        QuestionSetInsBo questionSetInsBo = new QuestionSetInsBo();
        questionSetInsBo.setQuestionSetId(entity.getQuestionSetId());
        questionSetInsBo.setQuestionSetInsId(entity.getId());
        questionSetInsBo.setUserId(entity.getUserId());
        return questionSetInsBo;
    }
}
