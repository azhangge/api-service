package com.huajie.educomponent.testpaper.service;

import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.pubrefer.constants.UserOperateType;
import com.huajie.educomponent.pubrefer.dao.UserOperateJpaRepo;
import com.huajie.educomponent.pubrefer.entity.UserOperateEntity;
import com.huajie.educomponent.questionbank.bo.QuestionBo;
import com.huajie.educomponent.questionbank.service.QuestionService;
import com.huajie.educomponent.testpaper.bo.*;
import com.huajie.educomponent.testpaper.dao.FavoriteQuestionSetJapRepo;
import com.huajie.educomponent.testpaper.dao.QuestionInJpaRepo;
import com.huajie.educomponent.testpaper.dao.QuestionSetJpaRepo;
import com.huajie.educomponent.testpaper.dao.QuestionSpeciesJpaRepo;
import com.huajie.educomponent.testpaper.entity.FavoriteQuestionSetEntity;
import com.huajie.educomponent.testpaper.entity.QuestionInEntity;
import com.huajie.educomponent.testpaper.entity.QuestionSetEntity;
import com.huajie.educomponent.testpaper.entity.QuestionSpeciesEntity;
import com.huajie.educomponent.usercenter.bo.UserBasicInfoBo;
import com.huajie.educomponent.usercenter.bo.UserRealInfoBo;
import com.huajie.educomponent.usercenter.service.UserBasicInfoService;
import com.huajie.educomponent.usercenter.service.UserRealInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgz on 2017/7/24.
 */
@Service
public class QuestionSetService {

    @Autowired
    private QuestionSetJpaRepo questionSetJpaRepo;

    @Autowired
    private QuestionInJpaRepo questionInJpaRepo;

    @Autowired
    private QuestionSpeciesJpaRepo questionSpeciesJpaRepo;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserBasicInfoService userBasicInfoService;

    @Autowired
    private UserOperateJpaRepo userOperateJpaRepo;

    @Autowired
    private UserRealInfoService userRealInfoService;

    @Autowired
    FavoriteQuestionSetJapRepo favoriteQuestionSetJapRepo;
    /**
     * 新建、更新试卷、题集
     *
     * @param userId
     * @param createBo
     * @return
     */
    public QuestionSetCreateBo saveQuestionSet(String userId, QuestionSetCreateBo createBo) {
        QuestionSetEntity entity = new QuestionSetEntity();
        //试卷属性
        if (createBo.getQuestionSetId().contains(EnvConstants.TEMP_ID_PREFIX)) {
            entity.updateCreateInfo(userId);
        } else {
            entity = questionSetJpaRepo.findOne(createBo.getQuestionSetId());
        }
        BeanUtils.copyProperties(createBo, entity);
        entity.updateModifyInfo(userId);
        entity = questionSetJpaRepo.save(entity);
        QuestionSetCreateBo result = new QuestionSetCreateBo();
        BeanUtils.copyProperties(entity, result);
        result.setQuestionSetId(entity.getId());
        //大小题
        if (createBo.getQuestionSetId().contains(EnvConstants.TEMP_ID_PREFIX)) {
            createBo.setQuestionSetId(entity.getId());
        }
        setQuestions(userId, createBo, result);
        return result;
    }

    /**
     * 题集试卷详情(questionSetId 可能是模板id，也可能是实例id)
     *
     * @param questionSetId
     * @return
     * @throws Exception
     */
    public QuestionSetBo getDetails(String userId, String questionSetId) {

        QuestionSetEntity questionSet = questionSetJpaRepo.findById(questionSetId);
        if (questionSet == null) {
            throw new BusinessException("不存在");
        }
        List<QuestionSpeciesEntity> speciesList = questionSpeciesJpaRepo.findByQuestionSetId(questionSetId);
        List<QuestionInEntity> questionIns = questionInJpaRepo.findByQuestionSetId(questionSetId);
        return getQuestionSetBo(userId, questionSet, speciesList, questionIns);
    }

    /**
     * 分页查询-简要
     *
     * @param userId
     * @param search
     * @param type
     * @param self
     * @param orderBy
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageResult<QuestionSetBriefBo> listPages(String userId, Boolean isPublic, String search, Integer type, Boolean self, Integer orderBy, int pageIndex, int pageSize) throws Exception {

        verifyArgument(userId, self);
        PageResult<QuestionSetEntity> questionSetBoPageResult = questionSetJpaRepo.listPages(userId, isPublic, search, type, self, orderBy, pageIndex, pageSize);
        List<QuestionSetBriefBo> questionSetBriefBos = new ArrayList<QuestionSetBriefBo>();
        for (QuestionSetEntity questionSetEntity : questionSetBoPageResult.getItems()) {
            QuestionSetBriefBo briefBo = new QuestionSetBriefBo();
            BeanUtils.copyProperties(questionSetEntity, briefBo);
            briefBo.setQuestionSetId(questionSetEntity.getId());
            if (!StringUtils.isEmpty(questionSetEntity.getCreatorId())) {
                UserRealInfoBo user = userRealInfoService.getUserRealInfo(questionSetEntity.getCreatorId());
                if (user != null && !StringUtils.isEmpty(user.getRealName())) {
                    briefBo.setCreatorName(user.getRealName());
                }
            }

            UserOperateEntity userOperateEntity = userOperateJpaRepo.findByIdAndType(questionSetEntity.getId(), userId, UserOperateType.FAVORITE_QUESTION_SET.getValue());
            if (userOperateEntity != null){
                briefBo.setIsFavorite(1);
            }
            questionSetBriefBos.add(briefBo);
        }
        PageResult<QuestionSetBriefBo> result = new PageResult<QuestionSetBriefBo>();
        result.setItems(questionSetBriefBos);
        result.setTotal(questionSetBoPageResult.getTotal());
        return result;
    }

    /**
     * 删除题集试卷试卷模板
     *
     * @param questionSetId
     */
    public void delete(String questionSetId) {
        QuestionSetEntity questionSetEntity = questionSetJpaRepo.findOne(questionSetId);
        if (questionSetEntity == null){
            throw new BusinessException("不存在");
        }
        questionSetEntity.setDeleted(Boolean.TRUE);
        questionSetJpaRepo.save(questionSetEntity);
    }

    /**
     * 详情数据转换
     *
     * @param questionSet
     * @param speciesList
     * @param questionIns
     * @return
     */
    public QuestionSetBo getQuestionSetBo(String userId, QuestionSetEntity questionSet, List<QuestionSpeciesEntity> speciesList, List<QuestionInEntity> questionIns) {
        QuestionSetBo result = new QuestionSetBo();
        BeanUtils.copyProperties(questionSet, result);
        result.setQuestionSetId(questionSet.getId());
        if (questionSet.getType() == 2) {
            convertToSpecies(result, speciesList);
        }
        UserRealInfoBo user = userRealInfoService.getUserRealInfo(questionSet.getCreatorId());
        if (user != null && !StringUtils.isEmpty(user.getRealName())) {
            result.setCreatorName(user.getRealName());
        }
        convertToQuestions(result, questionIns);
        UserOperateEntity userOperateEntity = userOperateJpaRepo.findByIdAndType(questionSet.getId(), userId, UserOperateType.FAVORITE_QUESTION_SET.getValue());
        if (userOperateEntity != null){
            result.setIsFavorite(1);
        }
        UserRealInfoBo userRealInfoBo = userRealInfoService.getUserRealInfo(questionSet.getCreatorId());
        if (userRealInfoBo != null && !StringUtils.isEmpty(userRealInfoBo.getRealName())){
            result.setCreatorName(userRealInfoBo.getRealName());
        }
        return result;
    }

    /**
     * 根据id查询简要信息
     * @param questionSetId
     * @return
     */
    public QuestionSetBriefBo getQuestionSetById(String questionSetId){
        return convertToBo(questionSetJpaRepo.findById(questionSetId));
    }

    /**
     * 实例试卷、题集不实例（本质上在模板基础上新建一条，替换questionSetId为实例questionSetInsId）
     * @param questionSetId
     * @return
     */
    public String questionSetInstance(String questionSetId){
        QuestionSetEntity questionSetEntity = questionSetJpaRepo.findById(questionSetId);
        QuestionInstanceBo questionInstanceBo = new QuestionInstanceBo();
        BeanUtils.copyProperties(questionSetEntity, questionInstanceBo);
        QuestionSetEntity questionSetIns = new QuestionSetEntity();
        BeanUtils.copyProperties(questionInstanceBo, questionSetIns);
        return questionSetJpaRepo.save(questionSetIns).getId();
    }


    public PageResult<QuestionSetBriefBo> searchQuestionSet(String userId, String search,Integer source, Integer type, Integer orderBy, int pageIndex, int pageSize) throws Exception {
        Boolean isPublic  = null;
        Boolean self = false;
        List<QuestionSetBriefBo> questionSetBriefBos = new ArrayList<QuestionSetBriefBo>();
        PageResult<QuestionSetBriefBo> result = new PageResult<QuestionSetBriefBo>();

        if(source != 2) {
            if(source == 3) {//题目题库
                isPublic = true;
                userId = null;
            } else if (source == 1) { //个人创建
                self = true;
            }
            PageResult<QuestionSetEntity> questionSetBoPageResult = questionSetJpaRepo.listPages(userId, isPublic, search, type, self, orderBy, pageIndex, pageSize);

            for (QuestionSetEntity questionSetEntity : questionSetBoPageResult.getItems()) {
                QuestionSetBriefBo briefBo = new QuestionSetBriefBo();
                BeanUtils.copyProperties(questionSetEntity, briefBo);
                briefBo.setQuestionSetId(questionSetEntity.getId());
                questionSetBriefBos.add(briefBo);
            }
            result.setTotal(questionSetBoPageResult.getTotal());
        } else {//个人收藏
            PageResult<FavoriteQuestionSetEntity> favoriteQuestionSetBoPageResult = favoriteQuestionSetJapRepo.listPages(userId, search, type, orderBy, pageIndex, pageSize);
            for (FavoriteQuestionSetEntity favoriteQuestionSetEntity : favoriteQuestionSetBoPageResult.getItems()) {
                QuestionSetBriefBo briefBo = new QuestionSetBriefBo();
                BeanUtils.copyProperties(favoriteQuestionSetEntity, briefBo);
                briefBo.setQuestionSetId(favoriteQuestionSetEntity.getNewQuestionSetEntity().getId());
                briefBo.setName(favoriteQuestionSetEntity.getNewQuestionSetEntity().getName());
                questionSetBriefBos.add(briefBo);
            }
            result.setTotal(favoriteQuestionSetBoPageResult.getTotal());
        }
        result.setItems(questionSetBriefBos);

        return result;
    }

    /**
     * 创建大题，关联小题
     *
     * @param userId
     * @param createBo
     * @throws Exception
     */
    private QuestionSetCreateBo setQuestions(String userId, QuestionSetCreateBo createBo, QuestionSetCreateBo result) {
        List<QuestionSpeciesBo> species = new ArrayList<QuestionSpeciesBo>();
        List<QuestionInCreateBo> questions = new ArrayList<QuestionInCreateBo>();
        //统一将之前的题目删掉，重新绑定
        List<QuestionInEntity> questionInEntities = questionInJpaRepo.findByQuestionSetId(createBo.getQuestionSetId());
        questionInJpaRepo.delete(questionInEntities);
        if (createBo.getType() == 1){
            saveTest(userId, createBo, questions);
        }else if (createBo.getType() == 2){
            savePaper(userId, createBo, species, questions);
        }
        result.setSpecies(species);
        result.setQuestions(questions);
        return result;
    }

    /**
     * 创建更新小题
     *
     * @param userId
     * @param questionInBo
     * @throws Exception
     */
    private QuestionInCreateBo setQuestionIn(String userId, QuestionInCreateBo questionInBo) {

        QuestionBo question = questionService.getOne(questionInBo.getQuestionId());
        if (question == null) {
            throw new BusinessException("试题不存在");
        }
        QuestionInEntity questionIn = new QuestionInEntity();
        if (StringUtils.isEmpty(questionInBo.getQuestionInId())) {
            questionIn.updateCreateInfo(userId);
        } else {
            questionIn = questionInJpaRepo.findOne(questionInBo.getQuestionInId());
            if (questionIn == null) {
                throw new BusinessException("不存在");
            }
        }
        questionIn.updateModifyInfo(userId);
        BeanUtils.copyProperties(questionInBo, questionIn);
        QuestionInEntity questionInEntity = questionInJpaRepo.save(questionIn);
        return convertToBo(questionInEntity);
    }

    /**
     * 自建题目。用户必须存在
     *
     * @param userId
     * @param self
     * @throws Exception
     */
    private void verifyArgument(String userId, Boolean self) {
        if (self != null && self == true) {
            if (StringUtils.isEmpty(userId)) {
                throw new BusinessException("参数错误");
            }
            UserBasicInfoBo user = userBasicInfoService.getUserBasicInfo(userId);
            if (user == null) {
                throw new BusinessException("用户未知");
            }
        }
    }

    /**
     * 获取练习答案
     * @param questionIns
     * @return
     */
    private List<TestPaperAnswerBo> getTestPaperAnswer(List<QuestionInEntity> questionIns){
        List<String> questionIds = new ArrayList<String>();
        for (QuestionInEntity entity:questionIns){
            questionIds.add(entity.getQuestionId());
        }
        List<QuestionBo> questions = questionService.getQuestions(questionIds);
        List<TestPaperAnswerBo> result = new ArrayList<TestPaperAnswerBo>();
        for (QuestionBo questionBo:questions){
            TestPaperAnswerBo bo = new TestPaperAnswerBo();
            BeanUtils.copyProperties(questionBo, bo);
            result.add(bo);
        }
        return result;
    }

    /**
     * 试卷大题
     */
    private void savePaper(String userId, QuestionSetCreateBo createBo, List<QuestionSpeciesBo> species, List<QuestionInCreateBo> questions){
        for (QuestionSpeciesBo speciesBo : createBo.getSpecies()) {
            QuestionSpeciesEntity speciesEntity = new QuestionSpeciesEntity();
            if (speciesBo.getSpeciesId().contains(EnvConstants.TEMP_ID_PREFIX)) {
                speciesEntity.updateCreateInfo(userId);
                speciesBo.setQuestionSetId(createBo.getQuestionSetId());
            } else {
                speciesEntity = questionSpeciesJpaRepo.findOne(speciesBo.getSpeciesId());
                if (speciesEntity == null) {
                    throw new BusinessException("不存在");
                }
            }
            speciesEntity.updateModifyInfo(userId);
            BeanUtils.copyProperties(speciesBo, speciesEntity);
            speciesEntity = questionSpeciesJpaRepo.save(speciesEntity);
            species.add(convertToBo(speciesEntity));
            savePaperQuestions(userId, createBo, speciesEntity.getId(), speciesBo, questions);
        }
    }

    /**
     * 试卷小题
     * @param userId
     * @param createBo
     */
    private void savePaperQuestions(String userId, QuestionSetCreateBo createBo, String speciesId, QuestionSpeciesBo speciesBo, List<QuestionInCreateBo> questions){
        for (QuestionInCreateBo questionInCreateBo : createBo.getQuestions()) {
            if (questionInCreateBo.getSpeciesId().equals(speciesBo.getSpeciesId())) {
                if (questionInCreateBo.getSpeciesId().contains(EnvConstants.TEMP_ID_PREFIX)) {
                    questionInCreateBo.setSpeciesId(speciesId);
                }
                questionInCreateBo.setQuestionSetId(createBo.getQuestionSetId());
                questionInCreateBo = setQuestionIn(userId, questionInCreateBo);
                questions.add(questionInCreateBo);
            }
        }
    }

    /**
     * 题集小题
     * @param userId
     * @param createBo
     */
    private void saveTest(String userId, QuestionSetCreateBo createBo, List<QuestionInCreateBo> questions){
        for (QuestionInCreateBo questionInCreateBo : createBo.getQuestions()) {
            questionInCreateBo.setQuestionSetId(createBo.getQuestionSetId());
            questionInCreateBo = setQuestionIn(userId, questionInCreateBo);
            questions.add(questionInCreateBo);
        }
    }

    /**
     * 大题转换
     * @param result
     * @param speciesList
     */
    private void convertToSpecies(QuestionSetBo result, List<QuestionSpeciesEntity> speciesList){
        List<QuestionSpeciesBo> species = new ArrayList<QuestionSpeciesBo>();
        for (QuestionSpeciesEntity speciesEntity : speciesList) {
            QuestionSpeciesBo bo = new QuestionSpeciesBo();
            BeanUtils.copyProperties(speciesEntity, bo);
            bo.setSpeciesId(speciesEntity.getId());
            species.add(bo);
        }
        result.setSpecies(species);
    }

    private void convertToQuestions(QuestionSetBo result, List<QuestionInEntity> questionIns){
        List<QuestionInBo> questions = new ArrayList<QuestionInBo>();
        for (QuestionInEntity questionInEntity : questionIns) {
            QuestionInBo bo = new QuestionInBo();
            BeanUtils.copyProperties(questionInEntity, bo);
            bo.setQuestionInId(questionInEntity.getId());
            QuestionBo questionBo = questionService.getOne(questionInEntity.getQuestionId());
            BeanUtils.copyProperties(questionBo, bo);
            questions.add(bo);
        }
        result.setQuestions(questions);
    }

    /**
     * toBo
     *
     * @param entity
     * @return
     */
    private QuestionSetBriefBo convertToBo(QuestionSetEntity entity) {
        if (entity == null) {
            return null;
        }
        QuestionSetBriefBo bo = new QuestionSetBriefBo();
        BeanUtils.copyProperties(entity, bo);
        bo.setQuestionSetId(entity.getId());
        return bo;
    }

    private QuestionSpeciesBo convertToBo(QuestionSpeciesEntity entity) {
        if (entity == null) {
            return null;
        }
        QuestionSpeciesBo bo = new QuestionSpeciesBo();
        BeanUtils.copyProperties(entity, bo);
        bo.setSpeciesId(entity.getId());
        return bo;
    }

    private QuestionInCreateBo convertToBo(QuestionInEntity entity) {
        if (entity == null) {
            return null;
        }
        QuestionInCreateBo bo = new QuestionInCreateBo();
        BeanUtils.copyProperties(entity, bo);
        bo.setQuestionInId(entity.getId());
        return bo;
    }
}
