package com.huajie.educomponent.pubrefer.service;

import com.huajie.appbase.BaseRetMessage;
import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.bo.CourseBasicBo;
import com.huajie.educomponent.course.service.CourseService;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.constants.UserOperateType;
import com.huajie.educomponent.pubrefer.dao.UserOperateJpaRepo;
import com.huajie.educomponent.course.entity.CourseBasicEntity;
import com.huajie.educomponent.pubrefer.bo.UserOperateBo;
import com.huajie.educomponent.pubrefer.entity.UserOperateEntity;
import com.huajie.educomponent.questionbank.bo.QuestionBriefBo;
import com.huajie.educomponent.questionbank.entity.QuestionEntity;
import com.huajie.educomponent.testpaper.bo.QuestionSetBriefBo;
import com.huajie.educomponent.testpaper.entity.QuestionSetEntity;
import com.huajie.educomponent.usercenter.bo.UserRealInfoBo;
import com.huajie.educomponent.usercenter.service.UserRealInfoService;
import com.huajie.utils.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 10070 on 2017/7/19.
 */
@Service
public class UserOperateService {

    @Autowired
    private UserOperateJpaRepo userOperateJpaRepo;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserRealInfoService userRealInfoService;

    /**
     * 用户课程、题目相关操作(批量)
     * @param operateBo
     * @return
     */
    public boolean operate(String userId, UserOperateBo operateBo) {
        //课程收藏、购买、学习、题目收藏、试卷收藏
        for (String objectId:operateBo.getObjectId()) {
            if (operateBo.getType() == UserOperateType.FAVORITE_COURSE.getValue() || operateBo.getType() == UserOperateType.BUY.getValue() || operateBo.getType() == UserOperateType.JOIN_IN.getValue()
                    || operateBo.getType() == UserOperateType.USER_LAST_ACCESS.getValue() || operateBo.getType() == UserOperateType.FAVORITE_QUESTION.getValue() || operateBo.getType() == UserOperateType.FAVORITE_QUESTION_SET.getValue()) {
                UserOperateEntity userCourseEntity = userOperateJpaRepo.findByIdAndType(objectId, userId, operateBo.getType());
                if (userCourseEntity != null && operateBo.getType() != UserOperateType.USER_LAST_ACCESS.getValue()) {
                    throw new BusinessException(BaseRetMessage.REPEAT_OPERATION.getValue());
                }
                saveUserOperate(objectId, operateBo.getType(), userId);
                //如果是购买，直接开始学习
                if (operateBo.getType().equals(UserOperateType.BUY.getValue())) {
                    operateBo.setType(UserOperateType.JOIN_IN.getValue());
                    saveUserOperate(objectId, operateBo.getType(), userId);
                }
                if (operateBo.getType().equals(UserOperateType.JOIN_IN.getValue()) || operateBo.getType().equals(UserOperateType.BUY.getValue())){
                    updateAccessCount(objectId, 1);
                }
                return true;
                //取消课程收藏、退出学习、取消题目收藏、取消试卷收藏
            } else if (operateBo.getType() == UserOperateType.UN_FAVORITE_COURSE.getValue() || operateBo.getType() == UserOperateType.UN_JOIN_IN.getValue() || operateBo.getType() == UserOperateType.UN_FAVORITE_QUESTION.getValue() || operateBo.getType() == UserOperateType.UN_FAVORITE_QUESTION_SET.getValue()) {
                deleteUserOperate(objectId, operateBo.getType(), userId);
                if (operateBo.getType().equals(UserOperateType.UN_JOIN_IN.getValue())){
                    updateAccessCount(objectId, -1);
                }
            }else {
                throw new BusinessException("参数异常");
            }
        }
        return true;
    }

    /**
     * 用户课程、题目查询
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageResult<Object> getUserOperate(String userId, Integer type, Boolean timeOut, int pageIndex, int pageSize){

        PageResult<Object> pageResult = userOperateJpaRepo.listPages(userId, type, timeOut, pageIndex, pageSize);
        if (type == 1 || type == 2 || type == 3 || type == 6){
            return convertToCourseBo(pageResult);
        }else if (type == 7){
            return convertToQuestionBo(pageResult);
        }else if (type == 9){
            return convertToQuestionSetBo(pageResult);
        }
        return null;
    }

    /**
     * 用户正向操作
     * @param objectId
     * @param userId
     */
    private void saveUserOperate(String objectId, Integer type, String userId){

        UserOperateEntity userOperate = new UserOperateEntity();
        userOperate.setObjectId(objectId);
        userOperate.setType(type);
        userOperate.setUserId(userId);
        userOperate.setTime(new Date());
        userOperateJpaRepo.save(userOperate);
    }

    /**
     * 用户反向操作
     * @param objectId
     * @param userId
     * @throws Exception
     */
    public void deleteUserOperate(String objectId, Integer type, String userId) {

        UserOperateEntity userCourseEntity = new UserOperateEntity();
        if (UserOperateType.UN_FAVORITE_COURSE.getValue() == type){
            userCourseEntity = userOperateJpaRepo.findByIdAndType(objectId, userId, UserOperateType.FAVORITE_COURSE.getValue());
        }
        if (UserOperateType.UN_JOIN_IN.getValue() == type){
            userCourseEntity = userOperateJpaRepo.findByIdAndType(objectId, userId, UserOperateType.JOIN_IN.getValue());
        }
        if (UserOperateType.UN_FAVORITE_QUESTION.getValue() == type){
            userCourseEntity = userOperateJpaRepo.findByIdAndType(objectId, userId, UserOperateType.JOIN_IN.getValue());
        }
        if (UserOperateType.UN_FAVORITE_QUESTION_SET.getValue() == type){
            userCourseEntity = userOperateJpaRepo.findByIdAndType(objectId, userId, UserOperateType.FAVORITE_QUESTION_SET.getValue());
        }

        if (userCourseEntity == null) {
            throw new BusinessException("操作错误");
        }
        userCourseEntity.setDeleted(true);
        userCourseEntity.setTime(new Date());
        userOperateJpaRepo.save(userCourseEntity);
    }

    /**
     * 更新课程学习人数
     * @param courseId
     */
    private void updateAccessCount(String courseId, int count){
            CourseBasicEntity courseBasicEntity = courseService.getCourseBrief(courseId);
            courseBasicEntity.setAccessCount(courseBasicEntity.getAccessCount() + count);
            courseService.svaeCourseBrief(courseBasicEntity);
    }

    private PageResult<Object> convertToCourseBo(PageResult<Object> pageResult){
        PageResult<Object> result = new PageResult<Object>();
        List<Object> objectList = new ArrayList<Object>();
        for (Object object:pageResult.getItems()){
            CourseBasicEntity courseBasicEntity = (CourseBasicEntity) object;
            CourseBasicBo courseBriefBo = new CourseBasicBo();
            BeanUtils.copyProperties(courseBasicEntity, courseBriefBo);
            courseBriefBo.setCourseId(courseBasicEntity.getId());
            if (!StringUtils.isEmpty(courseBriefBo.getThumbnailId())) {
                FileStorageBo file = fileStorageService.getStore(courseBriefBo.getThumbnailId());
                courseBriefBo.setThumbnailPath(PathUtils.getFilePath(file));
            }
            objectList.add(courseBriefBo);
        }
        result.setItems(objectList);
        result.setTotal(pageResult.getTotal());
        return result;
    }

    private PageResult<Object> convertToQuestionBo(PageResult<Object> pageResult){
        PageResult<Object> result = new PageResult<Object>();
        List<Object> objectList = new ArrayList<Object>();
        for (Object object:pageResult.getItems()){
            QuestionEntity questionEntity = (QuestionEntity) object;
            QuestionBriefBo questionBriefBo = new QuestionBriefBo();
            BeanUtils.copyProperties(questionEntity, questionBriefBo);
            questionBriefBo.setQuestionId(questionEntity.getId());
            objectList.add(questionBriefBo);
        }
        result.setItems(objectList);
        result.setTotal(pageResult.getTotal());
        return result;
    }

    private PageResult<Object> convertToQuestionSetBo(PageResult<Object> pageResult){
        PageResult<Object> result = new PageResult<Object>();
        List<Object> objectList = new ArrayList<Object>();
        for (Object object:pageResult.getItems()){
            QuestionSetEntity questionSetEntity = (QuestionSetEntity) object;
            QuestionSetBriefBo questionSetBriefBo = new QuestionSetBriefBo();
            BeanUtils.copyProperties(questionSetEntity, questionSetBriefBo);
            questionSetBriefBo.setQuestionSetId(questionSetEntity.getId());
            questionSetBriefBo.setIsFavorite(1);
            UserRealInfoBo userRealInfoBo = userRealInfoService.getUserRealInfo(questionSetEntity.getCreatorId());
            if (userRealInfoBo != null && !StringUtils.isEmpty(userRealInfoBo.getRealName())){
                questionSetBriefBo.setCreatorName(userRealInfoBo.getRealName());
            }
            objectList.add(questionSetBriefBo);
        }
        result.setItems(objectList);
        result.setTotal(pageResult.getTotal());
        return result;
    }

}
