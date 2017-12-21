package com.huajie.educomponent.questionbank.service;

import com.huajie.appbase.BaseRetMessage;
import com.huajie.educomponent.questionbank.bo.QuestionTypeBo;
import com.huajie.educomponent.questionbank.dao.QuestionTypeJpaRepo;
import com.huajie.educomponent.questionbank.entity.QuestionEntity;
import com.huajie.educomponent.questionbank.entity.QuestionTypeEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zgz on 2017/7/21.
 */
@Service
public class QuestionTypeService {

    @Autowired
    private QuestionTypeJpaRepo questionTypeJpaRepo;

    /**
     * 创建、更新
     * @param questionTypeBo
     * @return
     */
    public QuestionTypeBo save(String userId, QuestionTypeBo questionTypeBo) throws Exception {

        QuestionTypeEntity questionTypeEntity = new QuestionTypeEntity();
        if (StringUtils.isEmpty(questionTypeBo.getQuestionTypeId())){
            questionTypeEntity.setCreatorId(userId);
            questionTypeEntity.setCreateDate(new Date());
        }else {
            questionTypeEntity = questionTypeJpaRepo.findOne(questionTypeBo.getQuestionTypeId());
            if (questionTypeEntity == null){
                throw new Exception(BaseRetMessage.NOT_FIND.getValue());
            }
        }
        BeanUtils.copyProperties(questionTypeBo, questionTypeEntity);
        questionTypeEntity = questionTypeJpaRepo.save(questionTypeEntity);
        return convertToBo(questionTypeEntity);
    }

    /**
     * 获取所有
     * @return
     */
    public List<QuestionTypeBo> get(){
        List<QuestionTypeEntity> entityList = questionTypeJpaRepo.list();
        List<QuestionTypeBo> result = new ArrayList<QuestionTypeBo>();
        for (QuestionTypeEntity entity:entityList){
            result.add(convertToBo(entity));
        }
        return result;
    }

    /**
     * 删除
     * @param questionTypeId
     */
    public void delete(String questionTypeId) throws Exception {

        QuestionTypeEntity entity = questionTypeJpaRepo.findOne(questionTypeId);
        if (entity == null){
            throw new Exception("试题不存在");
        }
        entity.setDeleted(true);
        questionTypeJpaRepo.save(entity);
    }

    /**
     * toBo
     * @param entity
     * @return
     */
    private QuestionTypeBo convertToBo(QuestionTypeEntity entity){
        QuestionTypeBo bo = new QuestionTypeBo();
        if (entity != null){
            BeanUtils.copyProperties(entity, bo);
            bo.setQuestionTypeId(entity.getId());
        }
        return bo;
    }

}
