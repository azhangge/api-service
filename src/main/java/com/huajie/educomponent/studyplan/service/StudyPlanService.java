package com.huajie.educomponent.studyplan.service;

import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.service.FileStorageService;
import com.huajie.educomponent.studyplan.bo.StudyPlanBasicBo;
import com.huajie.educomponent.studyplan.bo.StudyPlanCreateBo;
import com.huajie.educomponent.studyplan.constants.StudyType;
import com.huajie.educomponent.studyplan.dao.StudyPlanBasicJpaRepo;
import com.huajie.educomponent.studyplan.dao.StudyPlanDetailJpaRepo;
import com.huajie.educomponent.studyplan.entity.StudyPlanBasicEntity;
import com.huajie.educomponent.studyplan.entity.StudyPlanDetailEntity;
import com.huajie.utils.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by xuxiaolong on 2017/10/31.
 */
@Service
public class StudyPlanService {

    @Autowired
    private StudyPlanBasicJpaRepo studyPlanBasicJpaRepo;

    @Autowired
    private StudyPlanDetailJpaRepo studyPlanDetailJpaRepo;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 创建/修改学习计划
     * @param studyPlanCreateBo
     * @return
     */
    public StudyPlanBasicBo save(String userId, StudyPlanCreateBo studyPlanCreateBo){
        StudyPlanBasicEntity studyPlanBasicEntity = new StudyPlanBasicEntity();
        if (StringUtils.isEmpty(studyPlanCreateBo.getPlanId())){
            studyPlanBasicEntity.updateCreateInfo(userId);
        }else {
            studyPlanBasicEntity = studyPlanBasicJpaRepo.findOne(studyPlanCreateBo.getPlanId());
            //学习计划已发布，不允许修改
            if (studyPlanBasicEntity.getStartTime().before(new Date())){
                throw new BusinessException("已经发布，不允许修改");
            }else{
                studyPlanDetailJpaRepo.deleteByPlanId(studyPlanCreateBo.getPlanId());
                studyPlanBasicEntity.updateModifyInfo(userId);
            }
        }
        BeanUtils.copyProperties(studyPlanCreateBo, studyPlanBasicEntity);
        studyPlanBasicEntity = studyPlanBasicJpaRepo.save(studyPlanBasicEntity);

        if (StringUtils.isEmpty(studyPlanCreateBo.getPlanId())) {
            studyPlanCreateBo.setPlanId(studyPlanBasicEntity.getId());
        }

        setQuestionSet(studyPlanCreateBo);
        setCourse(studyPlanCreateBo);
        setUser(studyPlanCreateBo);
        return convertToBasicBo(studyPlanBasicEntity);
    }

    /**
     * 设置学习计划里关联的题集
     * @param studyPlanCreateBo
     */
    private void setQuestionSet(StudyPlanCreateBo studyPlanCreateBo) {
        if (studyPlanCreateBo.getQuestionSetIds() == null || studyPlanCreateBo.getQuestionSetIds().size() < 1){
            return;
        }
        for (String setId:studyPlanCreateBo.getQuestionSetIds()){
            StudyPlanDetailEntity studyPlanDetailEntity = new StudyPlanDetailEntity();
            studyPlanDetailEntity.setType(StudyType.QUESTIONSET.getValue());
            studyPlanDetailEntity.setPlanId(studyPlanCreateBo.getPlanId());
            studyPlanDetailEntity.setAssociateId(setId);
            studyPlanDetailEntity.setIsRequired(false);
            studyPlanDetailJpaRepo.save(studyPlanDetailEntity);
        }
    }

    /**
     * 设置学习计划里的课程
     * @param studyPlanCreateBo
     */
    private void setCourse(StudyPlanCreateBo studyPlanCreateBo) {
        if (studyPlanCreateBo.getCourses() == null || studyPlanCreateBo.getCourses().size() < 1){
            return;
        }
        for (Map.Entry<String, Boolean> course : studyPlanCreateBo.getCourses().entrySet()){
            StudyPlanDetailEntity studyPlanDetailEntity = new StudyPlanDetailEntity();
            studyPlanDetailEntity.setType(StudyType.COURSE.getValue());
            studyPlanDetailEntity.setPlanId(studyPlanCreateBo.getPlanId());
            studyPlanDetailEntity.setAssociateId(course.getKey());
            studyPlanDetailEntity.setIsRequired(course.getValue());
            studyPlanDetailJpaRepo.save(studyPlanDetailEntity);
        }
    }

    /**
     * 设置学习计划里需要参加学习的用户
     * @param studyPlanCreateBo
     */
    private void setUser(StudyPlanCreateBo studyPlanCreateBo) {
        if (studyPlanCreateBo.getUserIds() == null || studyPlanCreateBo.getUserIds().size() < 1){
            return;
        }
        for (String userId:studyPlanCreateBo.getUserIds()){
            StudyPlanDetailEntity studyPlanDetailEntity = new StudyPlanDetailEntity();
            studyPlanDetailEntity.setType(StudyType.USER.getValue());
            studyPlanDetailEntity.setPlanId(studyPlanCreateBo.getPlanId());
            studyPlanDetailEntity.setAssociateId(userId);
            studyPlanDetailEntity.setIsRequired(false);
            studyPlanDetailJpaRepo.save(studyPlanDetailEntity);
        }
    }

    private StudyPlanCreateBo convertToCreateBo(StudyPlanBasicEntity entity){
        if (entity == null){
            return null;
        }
        StudyPlanCreateBo bo = new StudyPlanCreateBo();
        BeanUtils.copyProperties(entity, bo);
        bo.setPlanId(entity.getId());
        if (!StringUtils.isEmpty(entity.getThumbnailId())){
            FileStorageBo file = fileStorageService.getStore(entity.getThumbnailId());
            bo.setThumbnailPath(PathUtils.getFilePath(file));
        }
        return bo;
    }

    private StudyPlanBasicBo convertToBasicBo(StudyPlanBasicEntity entity){
        if (entity == null){
            return null;
        }
        StudyPlanBasicBo bo = new StudyPlanBasicBo();
        BeanUtils.copyProperties(entity, bo);
        bo.setPlanId(entity.getId());
        return bo;
    }

    /**
     * 删除学习计划
     *
     * @param userId, planId
     */
    public void deleteStudyPlan(String userId, String planId) {

        StudyPlanBasicEntity studyPlanBasicEntity = studyPlanBasicJpaRepo.findOne(planId);
        if (studyPlanBasicEntity == null) {
            throw new BusinessException("学习计划不存在！");
        }
        studyPlanBasicEntity.setDeleted(true);
        studyPlanBasicEntity.updateModifyInfo(userId);
        studyPlanBasicJpaRepo.save(studyPlanBasicEntity);
    }

    /**
     * 查看学习计划
     * @param planName
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageResult<StudyPlanCreateBo> getStudyPlans(String isPublish,String planName,int pageIndex, int pageSize) {
        Map<String,String> param = new HashMap<>();
        param.put("isPublish",isPublish);
        param.put("planName",planName);
        param.put("pageIndex",Integer.toString(pageIndex));
        param.put("pageSize",Integer.toString(pageSize));

        PageResult<StudyPlanCreateBo> result = getStudyPlanResult(param);

        return result;
    }

    /**
     * 用户查看自己的学习计划
     * @param userId
     * @param planName
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageResult<StudyPlanCreateBo> getUserStudyPlans(String userId,String isPublish, String planName, int pageIndex, int pageSize) {
        Map<String,String> param = new HashMap<>();
        param.put("userId",userId);
        param.put("isPublish",isPublish);
        param.put("planName",planName);
        param.put("pageIndex",Integer.toString(pageIndex));
        param.put("pageSize",Integer.toString(pageSize));

        PageResult<StudyPlanCreateBo> result = getStudyPlanResult(param);

        return result;
    }

    /**
     * 根据传入的参数，获取学习计划查询结果
     * @param param
     * @return
     */
    private PageResult<StudyPlanCreateBo> getStudyPlanResult(Map<String, String> param) {
        PageResult<StudyPlanBasicEntity> pageResult = studyPlanBasicJpaRepo.listPages(param);
        PageResult<StudyPlanCreateBo> result = new PageResult<StudyPlanCreateBo>();
        List<StudyPlanCreateBo> studyPlanCreateBos = new ArrayList<StudyPlanCreateBo>();
        for (StudyPlanBasicEntity basicEntity:pageResult.getItems()){
            StudyPlanCreateBo bo = convertToCreateBo(basicEntity);

            //获取学习计划关联用户、题集和课程
            List<String> questionSetIds  = new ArrayList<String>();
            Map<String,Boolean> courses  = new HashMap<String,Boolean>();
            List<String> userIds = new ArrayList<String>();

            List<StudyPlanDetailEntity> detailEntityList = studyPlanDetailJpaRepo.findByPlanId(basicEntity.getId());
            for(StudyPlanDetailEntity detailEntity:detailEntityList){
                if(StringUtils.isEmpty(param.get("userId"))){
                    if (StudyType.COURSE.getValue() == detailEntity.getType()){
                        courses.put(detailEntity.getAssociateId(),detailEntity.getIsRequired());
                    }else if(StudyType.QUESTIONSET.getValue() == detailEntity.getType()){
                        questionSetIds.add(detailEntity.getAssociateId());
                    }else if(StudyType.USER.getValue() == detailEntity.getType()){
                        userIds.add(detailEntity.getAssociateId());
                    }
                }else {
                    //用户查询不需要设置userIds
                    if (StudyType.COURSE.getValue() == detailEntity.getType()) {
                        courses.put(detailEntity.getAssociateId(), detailEntity.getIsRequired());
                    } else if (StudyType.QUESTIONSET.getValue() == detailEntity.getType()) {
                        questionSetIds.add(detailEntity.getAssociateId());
                    }
                }
            }
            bo.setCourses(courses);
            bo.setQuestionSetIds(questionSetIds);
            studyPlanCreateBos.add(bo);
        }
        result.setItems(studyPlanCreateBos);
        result.setTotal(pageResult.getTotal());

        return result;
    }
}