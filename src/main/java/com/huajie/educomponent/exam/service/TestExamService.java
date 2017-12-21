package com.huajie.educomponent.exam.service;

import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.exam.bo.answer.UserAnswerBo;
import com.huajie.educomponent.exam.bo.answer.UserSubmitAnswerBo;
import com.huajie.educomponent.exam.bo.answer.exam.ExamUserAnswerBriefBo;
import com.huajie.educomponent.exam.bo.answer.test.TestQuestionInsBo;
import com.huajie.educomponent.exam.bo.answer.test.TestQuestionSetInsBo;
import com.huajie.educomponent.exam.bo.answer.test.TestUserAnswerBriefBo;
import com.huajie.educomponent.exam.bo.exam.ExamQuestionInBo;
import com.huajie.educomponent.exam.bo.exam.ExamQuestionSetBo;
import com.huajie.educomponent.exam.bo.exam.ExamQuestionSpeciesBo;
import com.huajie.educomponent.exam.bo.test.TestQuestionInBo;
import com.huajie.educomponent.exam.bo.test.TestQuestionSetBo;
import com.huajie.educomponent.exam.bo.test.TestQuestionSpeciesBo;
import com.huajie.educomponent.exam.dao.ExamNoticeJpaRepo;
import com.huajie.educomponent.exam.dao.ExamPaperTemplateJpaRepo;
import com.huajie.educomponent.exam.dao.ExamUserPaperJpaRepo;
import com.huajie.educomponent.exam.entity.ExamNoticeEntity;
import com.huajie.educomponent.exam.entity.ExamPaperTemplateEntity;
import com.huajie.educomponent.exam.entity.ExamUserPaperEntity;
import com.huajie.educomponent.pubrefer.constants.UserOperateType;
import com.huajie.educomponent.pubrefer.dao.UserOperateJpaRepo;
import com.huajie.educomponent.pubrefer.entity.UserOperateEntity;
import com.huajie.educomponent.testpaper.bo.*;
import com.huajie.educomponent.testpaper.dao.UserAnswerJpaRepo;
import com.huajie.educomponent.questionbank.bo.QuestionBo;
import com.huajie.educomponent.questionbank.service.QuestionService;
import com.huajie.educomponent.testpaper.entity.UserAnswerEntity;
import com.huajie.educomponent.testpaper.service.QuestionInService;
import com.huajie.educomponent.testpaper.service.QuestionSetInsService;
import com.huajie.educomponent.testpaper.service.QuestionSetService;
import com.huajie.educomponent.testpaper.service.UserAnswerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * Created by zgz on 2017/9/7.
 */
@Service
public class TestExamService {

    @Autowired
    private QuestionSetService questionSetService;

    @Autowired
    private QuestionSetInsService questionSetInsService;

    @Autowired
    private UserAnswerJpaRepo userAnswerJpaRepo;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamNoticeJpaRepo examNoticeJpaRepo;

    @Autowired
    private ExamPaperTemplateJpaRepo examPaperTemplateJpaRepo;

    @Autowired
    private QuestionInService questionInService;

    @Autowired
    private ExamUserPaperJpaRepo examUserPaperJpaRepo;

    @Autowired
    private UserAnswerService userAnswerService;


    /**
     * 开始练习
     * @param questionSetId
     * @return
     */
    public TestQuestionSetBo startTest(String userId, String questionSetId) {
        QuestionSetBo questionSetBo = questionSetService.getDetails(userId, questionSetId);
        return convertToTestBo(questionSetBo);
    }

    /**
     * 开始考试
     */
    public ExamQuestionSetBo startExam(String userId, String examId){
        examVerify(examId);
        ExamPaperTemplateEntity examPaperTemplate = examPaperTemplateJpaRepo.findByExamId(examId);
        if (examPaperTemplate == null || StringUtils.isEmpty(examPaperTemplate.getPaperId())){
            throw new BusinessException("考试未关联试卷、无效考试");
        }
        //获取考试卷
        QuestionSetBo questionSetBo = questionSetService.getDetails(userId, examPaperTemplate.getPaperId());
        if (questionSetBo.getType() == null || questionSetBo.getType() != 2){
            throw new BusinessException("不存在");
        }
        //实例
        QuestionSetInsBo questionSetInsBo = questionSetInsService.save(userId, examPaperTemplate.getPaperId(), 2);
        //开考信息保存
        saveUserExamStartData(userId, examId, questionSetInsBo);
        // TODO: 2017/9/23  若是随机卷，新建题目关联
        return convertToExamBo(questionSetBo, questionSetInsBo.getQuestionSetInsId());
    }

    /**
     * 提交练习作答,每次提交保存一次记录，不是更新之前的
     * 判断对错  保存答题记录和答题对错
     * @param userId
     * @param userAnswer
     * @return
     */
    public String submit(String userId, UserSubmitAnswerBo userAnswer){
        verify(userAnswer);
        //创建test-->testIns实例关系
        //判题
        List<String> questionIds = new ArrayList<String>();
        for (UserAnswerBo userUserAnswerBo :userAnswer.getAnswers()){
            questionIds.add(userUserAnswerBo.getQuestionId());
        }
        List<QuestionBo> questionBos = questionService.getQuestions(questionIds);
        //练习实例化，1 questionSetId替换  2 questionSet和questionSetIns对应关系
        String questionSetInsId = setTestQuestionSetIns(userId, userAnswer.getQuestionSetId(), userAnswer.getType());
        if (StringUtils.isEmpty(userAnswer.getQuestionSetInsId())) {
            userAnswer.setQuestionSetInsId(questionSetInsId);
        }
        setUserAnswer(userId, userAnswer, questionBos);
        //如果是考试，计算得分、是否及格，保存相关数据
        if (userAnswer.getType() == 2){
            saveUserExamSubmitData(userId, userAnswer);
        }
        return userAnswer.getQuestionSetInsId();
    }

    /**
     * 用户查询练习作答记录列表
     * @param userId
     * @return
     */
    public PageResult<TestUserAnswerBriefBo> getTestList(String userId, int pageIndex, int pageSize){
        PageResult<ExamUserAnswerBriefBo> userAnswerBriefs = getUserSubmitList(userId, 1, pageIndex, pageSize);
        return convertToTestAnswer(userAnswerBriefs);
    }

    /**
     * 用户查询考试作答记录列表
     * @param userId
     * @return
     */
    public PageResult<ExamUserAnswerBriefBo> getExamList(String userId, int pageIndex, int pageSize){
        return getUserSubmitList(userId, 2, pageIndex, pageSize);
    }

    /**
     * 用户查询考试作答记录列表
     * @param userId
     * @return
     */
    public PageResult<ExamUserAnswerBriefBo> getUserSubmitList(String userId, Integer type, int pageIndex, int pageSize){
        PageResult<QuestionSetInsBo> questionSetInsBoList = questionSetInsService.getInsById(userId, type, pageIndex, pageSize);
        PageResult<ExamUserAnswerBriefBo> result = new PageResult<ExamUserAnswerBriefBo>();
        List<ExamUserAnswerBriefBo> list = new ArrayList<ExamUserAnswerBriefBo>();
        for (QuestionSetInsBo questionSetInsBo:questionSetInsBoList.getItems()){
            ExamUserAnswerBriefBo examQuestionSetHisBo = getTestInsAnswerBrief(questionSetInsBo.getUserId(), questionSetInsBo.getQuestionSetInsId(), questionSetInsBo.getQuestionSetId());
            examQuestionSetHisBo.setQuestionSetId(questionSetInsBo.getQuestionSetId());
            examQuestionSetHisBo.setQuestionSetInsId(questionSetInsBo.getQuestionSetInsId());
            if (type == 2) {
                ExamUserPaperEntity exam = examUserPaperJpaRepo.findByInsId(questionSetInsBo.getQuestionSetInsId());
                if (exam != null) {
                    examQuestionSetHisBo.setExamId(exam.getExamId());
                }
            }
            list.add(examQuestionSetHisBo);
        }
        result.setItems(list);
        result.setTotal(questionSetInsBoList.getTotal());
        return result;
    }

    /**
     * 获取练习某次作答的详情
     * @param questionSetInsId
     * @return
     */
    public TestQuestionSetInsBo getTestInsAnswer(String userId, String questionSetInsId){
        QuestionSetInsBo questionSetIns = questionSetInsService.get(questionSetInsId);
        if (questionSetIns == null){
            throw new BusinessException("不存在");
        }
        TestQuestionSetInsBo result = new TestQuestionSetInsBo();
        QuestionSetBo questionSetInsBo = questionSetService.getDetails(userId, questionSetIns.getQuestionSetId());
        getTestBrief(result, userId, questionSetInsId, questionSetInsBo);
        getSpecies(result, questionSetInsBo);
        getQuestions(result, questionSetInsId, questionSetInsBo);
        return result;
    }

    /**
     * 统计某一次作答记录
     * @param questionSetInsId
     * @return
     */
    public ExamUserAnswerBriefBo getTestInsAnswerBrief(String userId, String questionSetInsId, String questionSetId){
        List<UserAnswerEntity> userAnswers = userAnswerJpaRepo.findByUserIdAndInstanceId(userId, questionSetInsId);
        ExamUserAnswerBriefBo result = new ExamUserAnswerBriefBo();
        int rightNum = 0;
        float userScore = 0;
        for (UserAnswerEntity userAnswerEntity:userAnswers){
            result.setSubmitTime(userAnswerEntity.getSubmitTime());
            if (userAnswerEntity.getIsRight().equals(Boolean.TRUE)){
                rightNum = rightNum + 1;
                if (userAnswerEntity.getReal_score() != null) {
                    userScore = userScore + userAnswerEntity.getReal_score();
                }
            }
        }
        result.setUserScore(userScore);
        result.setRightNum(rightNum);
        QuestionSetBriefBo questionSetBriefBo = questionSetService.getQuestionSetById(questionSetId);
        result.setQuestionNum(questionSetBriefBo.getQuestionNum());
        result.setName(questionSetBriefBo.getName());
        result.setType(questionSetBriefBo.getType());
        return result;
    }

    /**
     * 练习或试卷校验
     * @param userAnswer
     */
    private void verify(UserSubmitAnswerBo userAnswer){
        if (userAnswer.getType() == 2 && StringUtils.isEmpty(userAnswer.getQuestionSetInsId())){
            throw new BusinessException("参数错误");
        }
        if (StringUtils.isEmpty(userAnswer.getQuestionSetInsId())){
            QuestionSetBriefBo questionSetBriefBo = questionSetService.getQuestionSetById(userAnswer.getQuestionSetId());
            if (questionSetBriefBo == null){
                throw new BusinessException("练习不存在、不能交卷");
            }
        }else {
            QuestionSetInsBo questionSetInsBo = questionSetInsService.get(userAnswer.getQuestionSetInsId());
            if (questionSetInsBo == null){
                throw new BusinessException("考试不存在、不能交卷");
            }
        }
    }

    /**
     * 保存用户答案
     * @param userId
     * @param userAnswer
     * @param questionBos
     */
    private void setUserAnswer(String userId, UserSubmitAnswerBo userAnswer, List<QuestionBo> questionBos){
        QuestionSetBriefBo questionSetBriefBo = questionSetService.getQuestionSetById(userAnswer.getQuestionSetId());
        for (UserAnswerBo userAnswerBo :userAnswer.getAnswers()) {
            for (QuestionBo questionBo : questionBos) {
                if (userAnswerBo.getQuestionId().equals(questionBo.getQuestionId())) {
                    UserAnswerEntity answerEntity = new UserAnswerEntity();
                    BeanUtils.copyProperties(userAnswerBo, answerEntity);
                    setUserAnswerEntity(answerEntity, userId, userAnswerBo, questionBo, userAnswer, questionSetBriefBo);
                    userAnswerJpaRepo.save(answerEntity);
                }
            }
        }
    }

    /**
     * 实例insId--setId
     * @param userId
     * @param questionSetId
     * @return
     */
    private String setTestQuestionSetIns(String userId, String questionSetId, int type){
        if (type == 1){
            return questionSetInsService.save(userId, questionSetId, type).getQuestionSetInsId();
        }
        return null;
    }

    /**
     * 获取简要信息
     * @param result
     * @param questionSetInsBo
     */
    private void getTestBrief(TestQuestionSetInsBo result, String userId, String questionSetInsId, QuestionSetBo questionSetInsBo){
        ExamUserAnswerBriefBo testQuestionSetHisBo = getTestInsAnswerBrief(userId, questionSetInsId, questionSetInsBo.getQuestionSetId());
        BeanUtils.copyProperties(testQuestionSetHisBo, result);
        result.setQuestionSetInsId(questionSetInsId);
        result.setQuestionSetId(questionSetInsBo.getQuestionSetId());
    }

    /**
     * 获取大题
     * @param result
     * @param questionSetInsBo
     */
    private void getSpecies(TestQuestionSetInsBo result, QuestionSetBo questionSetInsBo){
        if (questionSetInsBo.getType() == 2) {
            List<TestQuestionSpeciesBo> species = new ArrayList<TestQuestionSpeciesBo>();
            for (QuestionSpeciesBo questionSpeciesBo : questionSetInsBo.getSpecies()) {
                TestQuestionSpeciesBo testQuestionSpeciesBo = new TestQuestionSpeciesBo();
                BeanUtils.copyProperties(questionSpeciesBo, testQuestionSpeciesBo);
                species.add(testQuestionSpeciesBo);
            }
            result.setSpecies(species);
        }
    }

    /**
     * 题目
     * @param result
     * @param questionSetInsId
     * @param questionSetInsBo
     */
    private void getQuestions(TestQuestionSetInsBo result, String questionSetInsId, QuestionSetBo questionSetInsBo){
        List<TestQuestionInsBo> questions = new ArrayList<TestQuestionInsBo>();
        for (QuestionInBo questionInBo:questionSetInsBo.getQuestions()){
            TestQuestionInsBo testQuestionInsBo = new TestQuestionInsBo();
            BeanUtils.copyProperties(questionInBo, testQuestionInsBo);
            UserAnswerEntity userAnswer = userAnswerJpaRepo.findByInstanceIdAndQuestionId(questionSetInsId, questionInBo.getQuestionId());
            BeanUtils.copyProperties(userAnswer, testQuestionInsBo);
            questions.add(testQuestionInsBo);
        }
        result.setQuestions(questions);
    }

    /**
     * 校验
     */
    private void examVerify(String examId){
        ExamNoticeEntity exam = examNoticeJpaRepo.findOne(examId);
        if (exam == null){
            throw new BusinessException("考试不存在");
        }
        if (exam.getIsPublic() == false){
            throw new BusinessException("考试未公开");
        }
        if (exam.getIsPublish() == false){
            throw new BusinessException("考试未发布");
        }
    }

    /**
     * 保存用户开始考试数据
     * @param userId
     * @param examId
     * @param questionSetInsBo
     */
    private void saveUserExamStartData(String userId, String examId, QuestionSetInsBo questionSetInsBo){
        ExamUserPaperEntity examUserPaperEntity = new ExamUserPaperEntity();
        examUserPaperEntity.setUserId(userId);
        examUserPaperEntity.setExamId(examId);
        examUserPaperEntity.setQuestionSetInsId(questionSetInsBo.getQuestionSetInsId());
        examUserPaperEntity.setStartTime(new Date());
        examUserPaperJpaRepo.save(examUserPaperEntity);
    }

    /**
     * 保存用户交卷数据、考试得分、交卷时间、是否及格
     * @param userId
     * @param userAnswer
     */
    private void saveUserExamSubmitData(String userId, UserSubmitAnswerBo userAnswer){
        ExamUserPaperEntity examUserPaperEntity = examUserPaperJpaRepo.findByInsId(userAnswer.getQuestionSetInsId());
        examUserPaperEntity.setSubmitTime(new Date());
        ExamUserAnswerRetBo userAnswerRetBo = userAnswerService.getExamTotalScore(userId, userAnswer.getQuestionSetInsId());
        examUserPaperEntity.setScore(userAnswerRetBo.getScore());
        examUserPaperEntity.setIsPass(userAnswerRetBo.getIsPass());
        examUserPaperJpaRepo.save(examUserPaperEntity);
    }

    /**
     * 设置试题数据来保存
     * @param answerEntity
     * @param userId
     * @param userAnswerBo
     * @param questionBo
     * @param userAnswer
     * @param questionSetBriefBo
     */
    private void setUserAnswerEntity(UserAnswerEntity answerEntity, String userId, UserAnswerBo userAnswerBo, QuestionBo questionBo,UserSubmitAnswerBo userAnswer, QuestionSetBriefBo questionSetBriefBo){
        answerEntity.setSubmitTime(new Date());
        answerEntity.setUserId(userId);
        if (userAnswerBo.getUserAnswer().equalsIgnoreCase(questionBo.getAnswer())) {
            answerEntity.setIsRight(Boolean.TRUE);
            if (userAnswer.getType() == 2) {
                if (questionSetBriefBo.getStrategy() == 1) {
                    answerEntity.setReal_score(questionInService.getPaperQuestionScore(userAnswer.getQuestionSetId(), userAnswerBo.getQuestionId()));
                }else {
                    answerEntity.setReal_score(questionInService.getPaperQuestionScore(userAnswer.getQuestionSetInsId(), userAnswerBo.getQuestionId()));
                }
            }
        } else {
            answerEntity.setIsRight(Boolean.FALSE);
            answerEntity.setReal_score(0f);
        }
        answerEntity.setQuestionSetInsId(userAnswer.getQuestionSetInsId());
    }

    /**
     * toTestUserAnswerBriefBo
     * @param userAnswerBriefs
     * @return
     */
    private PageResult<TestUserAnswerBriefBo> convertToTestAnswer(PageResult<ExamUserAnswerBriefBo> userAnswerBriefs){
        PageResult<TestUserAnswerBriefBo> result = new PageResult<TestUserAnswerBriefBo>();
        List<TestUserAnswerBriefBo> bos = new ArrayList<TestUserAnswerBriefBo>();
        for (ExamUserAnswerBriefBo examUserAnswerBriefBo:userAnswerBriefs.getItems()){
            TestUserAnswerBriefBo testUserAnswerBriefBo = new TestUserAnswerBriefBo();
            BeanUtils.copyProperties(examUserAnswerBriefBo, testUserAnswerBriefBo);
            bos.add(testUserAnswerBriefBo);
        }
        result.setItems(bos);
        result.setTotal(userAnswerBriefs.getTotal());
        return result;
    }
    /**
     * toTestBo
     * @param questionSetBo
     * @return
     */
    private TestQuestionSetBo convertToTestBo(QuestionSetBo questionSetBo){
        if (questionSetBo == null){
            return null;
        }
        TestQuestionSetBo bo = new TestQuestionSetBo();
        BeanUtils.copyProperties(questionSetBo, bo);
        List<TestQuestionInBo> questions = new ArrayList<TestQuestionInBo>();
        if (questionSetBo.getType() == 2) {
            List<TestQuestionSpeciesBo> species = new ArrayList<TestQuestionSpeciesBo>();
            for (QuestionSpeciesBo speciesBo : questionSetBo.getSpecies()) {
                TestQuestionSpeciesBo testSpeciesBo = new TestQuestionSpeciesBo();
                BeanUtils.copyProperties(speciesBo, testSpeciesBo);
                species.add(testSpeciesBo);
            }
            bo.setSpecies(species);
        }
        for (QuestionInBo inBo:questionSetBo.getQuestions()){
            TestQuestionInBo testInBo = new TestQuestionInBo();
            BeanUtils.copyProperties(inBo, testInBo);
            questions.add(testInBo);
        }
        bo.setQuestions(questions);
        return bo;
    }

    /**
     * toExamBo
     * @param questionSetBo
     * @return
     */
    private ExamQuestionSetBo convertToExamBo(QuestionSetBo questionSetBo, String questionSetInsId){
        if (questionSetBo == null){
            return null;
        }
        ExamQuestionSetBo bo = new ExamQuestionSetBo();
        BeanUtils.copyProperties(questionSetBo, bo);
        List<ExamQuestionSpeciesBo> species = new ArrayList<ExamQuestionSpeciesBo>();
        List<ExamQuestionInBo> questions = new ArrayList<ExamQuestionInBo>();
        for (QuestionSpeciesBo speciesBo:questionSetBo.getSpecies()){
            ExamQuestionSpeciesBo examSpeciesBo = new ExamQuestionSpeciesBo();
            BeanUtils.copyProperties(speciesBo, examSpeciesBo);
            species.add(examSpeciesBo);
        }
        for (QuestionInBo inBo:questionSetBo.getQuestions()){
            ExamQuestionInBo testInBo = new ExamQuestionInBo();
            BeanUtils.copyProperties(inBo, testInBo);
            questions.add(testInBo);
        }
        bo.setSpecies(species);
        bo.setQuestions(questions);
        bo.setQuestionSetInsId(questionSetInsId);
        return bo;
    }
}
