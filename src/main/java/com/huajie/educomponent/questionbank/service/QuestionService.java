package com.huajie.educomponent.questionbank.service;

import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.questionbank.bo.QuestionBo;
import com.huajie.educomponent.questionbank.bo.QuestionBriefBo;
import com.huajie.educomponent.questionbank.constants.QuestionType;
import com.huajie.educomponent.questionbank.constants.UserBanksType;
import com.huajie.educomponent.questionbank.dao.QuestionJpaRepo;
import com.huajie.educomponent.questionbank.entity.QuestionEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zgz on 2017/7/21.
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionJpaRepo questionRepo;

    /**
     * 新建、更新试题
     *
     * @param userId
     * @param questionBo
     * @return
     * @throws Exception
     */
    public String save(String userId, QuestionBo questionBo) {
        if (QuestionType.SINGLE_CHOICE.getValue() > questionBo.getType() || QuestionType.Q_A.getValue() < questionBo.getType()) {
            throw new BusinessException("参数错误");
        }
        QuestionEntity questionEntity = new QuestionEntity();
        if (StringUtils.isEmpty(questionBo.getQuestionId())) {
            questionEntity.updateCreateInfo(userId);
        } else {
            questionEntity = questionRepo.findOne(questionBo.getQuestionId());
            if (questionEntity == null) {
                throw new BusinessException("试题不存在");
            }
        }
        questionEntity.updateModifyInfo(userId);
        BeanUtils.copyProperties(questionBo, questionEntity);
        questionEntity = questionRepo.save(questionEntity);
        return questionEntity.getId();
    }

    /**
     * 单个试题
     *
     * @param questionId
     * @return
     * @throws Exception
     */
    public QuestionBo getOne(String questionId) {

        QuestionEntity entity = questionRepo.findOne(questionId);
        if (entity == null) {
            throw new BusinessException("试题不存在");
        }
        return convertToBo(entity);
    }

    /**
     * 试题分页查询(公开的)
     *
     * @param search
     * @param type
     * @param mainCategory
     * @param subCategory
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageResult<QuestionBriefBo> listPages(String userId, String search, Integer type, String mainCategory, String subCategory, int pageIndex, int pageSize) {

        PageResult<QuestionBriefBo> result = new PageResult<QuestionBriefBo>();
        PageResult<QuestionEntity> questions = questionRepo.listPages(userId, search, type, 1, mainCategory, subCategory, pageIndex, pageSize);
        List<QuestionBriefBo> questionBos = convertToBriefBos(questions.getItems());
        result.setTotal(questions.getTotal());
        result.setItems(questionBos);
        return result;
    }

    /**
     * 删除试题
     *
     * @param questionId
     */
    public void delete(String questionId) {
        QuestionEntity entity = questionRepo.findOne(questionId);
        if (entity == null) {
            throw new BusinessException("试题不存在");
        }
        entity.setDeleted(true);
        questionRepo.save(entity);
    }

    /**
     * 个人题库
     *
     * @param userId
     * @param action 1、个人创建的题目，2、收藏题目；3、公开的，2、错题；
     * @param search
     * @param type
     * @param mainCategory
     * @param subCategory
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageResult<QuestionBriefBo> userBanks(String userId, Integer action, String search, Integer type, String mainCategory, String subCategory, int pageIndex, int pageSize) {

        PageResult<QuestionEntity> questions = new PageResult<QuestionEntity>();
        if (action == null){
            questions = questionRepo.listPages(null, search, type, 1, mainCategory, subCategory, pageIndex, pageSize);
        }else {
            if (action < UserBanksType.USER_CREATE_QUESTION.getValue() || action > UserBanksType.ERROR_QUESTION.getValue()) {
                return null;
            }
            //收藏的题目
            if (action == UserBanksType.FAVORITE_QUESTION.getValue()) {
                if (StringUtils.isEmpty(userId)) {
                    throw new BusinessException("请先登录");
                }
                questions = questionRepo.userBanks(userId, action, search, type, mainCategory, subCategory, pageIndex, pageSize);
            }
            //错题
            else if (action == UserBanksType.ERROR_QUESTION.getValue()) {

            } else if (action == UserBanksType.OPEN_QUESTION.getValue()) {
                questions = questionRepo.listPages(null, search, type, 1, mainCategory, subCategory, pageIndex, pageSize);
            }
            //个人创建题目
            else if (action == UserBanksType.USER_CREATE_QUESTION.getValue()) {
                questions = questionRepo.listPages(userId, search, type, null, mainCategory, subCategory, pageIndex, pageSize);
            }
        }
        PageResult<QuestionBriefBo> result = new PageResult<QuestionBriefBo>();
        List<QuestionBriefBo> briefBos = convertToBriefBos(questions.getItems());
        result.setTotal(questions.getTotal());
        result.setItems(briefBos);
        return result;
    }

    /**
     * 批量查询
     * @param questionIds
     * @return
     */
    public List<QuestionBo> getQuestions(List<String> questionIds){
        List<QuestionEntity>  questions = questionRepo.findByIds(questionIds);
        List<QuestionBo> result = new ArrayList<QuestionBo>();
        for (QuestionEntity entity:questions){
            result.add(convertToBo(entity));
        }
        return result;
    }
    /**
     * toBo
     * @param entity
     * @return
     */
    private QuestionBo convertToBo(QuestionEntity entity) {
        if (entity == null) {
            return null;
        }
        QuestionBo bo = new QuestionBo();
        BeanUtils.copyProperties(entity, bo);
        bo.setQuestionId(entity.getId());
        return bo;
    }

    /**
     * toEntity
     * @param bo
     * @return
     */
    private QuestionEntity convertToEntity(QuestionBo bo) {
        QuestionEntity entity = new QuestionEntity();
        if (bo != null) {
            BeanUtils.copyProperties(bo, entity);
        }
        entity.setId(bo.getQuestionId());
        return entity;
    }

    /**
     * convertToBriefBos
     * @param entities
     * @return
     */
    private List<QuestionBriefBo> convertToBriefBos(Collection<QuestionEntity> entities) {
        List<QuestionBriefBo> questionBos = new ArrayList<QuestionBriefBo>();
        for (QuestionEntity entity : entities) {
            QuestionBriefBo questionBriefBo = new QuestionBriefBo();
            BeanUtils.copyProperties(entity, questionBriefBo);
            questionBriefBo.setQuestionId(entity.getId());
            //Todo 填空和问答可能有小题
            questionBos.add(questionBriefBo);
        }
        return questionBos;
    }
}
