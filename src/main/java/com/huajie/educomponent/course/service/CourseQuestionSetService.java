package com.huajie.educomponent.course.service;

import com.huajie.educomponent.course.bo.CourseQuestionSetBo;
import com.huajie.educomponent.course.dao.CourseQuestionSetJpaRepo;
import com.huajie.educomponent.course.entity.CourseQuestionSetEntity;
import com.huajie.educomponent.testpaper.bo.QuestionSetBriefBo;
import com.huajie.educomponent.testpaper.service.QuestionSetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CourseQuestionSetService {

    @Autowired
    private CourseQuestionSetJpaRepo courseQuestionSetJpaRepo;

    @Autowired
    private QuestionSetService questionSetService;

    public void save(String courseId, CourseQuestionSetBo courseQuestionSetBo){
        List<CourseQuestionSetEntity> courseQuestionSetEntities = courseQuestionSetJpaRepo.findByCourseId(courseId);
        courseQuestionSetJpaRepo.delete(courseQuestionSetEntities);
        List<CourseQuestionSetEntity> entities = new ArrayList<>();
        for (String questionSetId : courseQuestionSetBo.getQuestionSetIds()){
            CourseQuestionSetEntity courseQuestionSetEntity = new CourseQuestionSetEntity();
            courseQuestionSetEntity.setCourseId(courseId);
            courseQuestionSetEntity.setQuestionSetId(questionSetId);
            courseQuestionSetEntity.setCreateTime(new Date());
            entities.add(courseQuestionSetEntity);
        }
        courseQuestionSetJpaRepo.save(entities);
    }

    public List<QuestionSetBriefBo> getCourseQuestionSet(String courseId){
        List<CourseQuestionSetEntity> courseQuestionSetEntities = courseQuestionSetJpaRepo.findByCourseId(courseId);
        List<QuestionSetBriefBo> result = new ArrayList<>();
        for (CourseQuestionSetEntity courseQuestionSetEntity:courseQuestionSetEntities){
            QuestionSetBriefBo bo = questionSetService.getQuestionSetById(courseQuestionSetEntity.getQuestionSetId());
            result.add(bo);
        }
        return result;
    }
}
