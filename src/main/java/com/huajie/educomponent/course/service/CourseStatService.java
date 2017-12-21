package com.huajie.educomponent.course.service;

import com.huajie.appbase.BusinessException;
import com.huajie.educomponent.course.bo.CourseChapterStudyBo;
import com.huajie.educomponent.course.bo.CourseChapterStudyDetailBo;
import com.huajie.educomponent.course.dao.CourseChapterUserStatJpaRepo;
import com.huajie.educomponent.course.entity.CourseChapterUserStatEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class CourseStatService {

    @Autowired
    private CourseChapterUserStatJpaRepo courseChapterUserStatJpaRepo;

    public void saveChapterStudy(String userId, CourseChapterStudyBo courseChapterStudyBo) {
        verify(courseChapterStudyBo);
        CourseChapterUserStatEntity entity = new CourseChapterUserStatEntity();
        CourseChapterUserStatEntity entityRef = courseChapterUserStatJpaRepo.findByChapterId(courseChapterStudyBo.getChapterId());
        if (entityRef == null){
            entity.setUserId(userId);
            entity.setChapterId(courseChapterStudyBo.getChapterId());
            entity.setTotalStudyTime(courseChapterStudyBo.getStudyTime());
        }else{
            entity = entityRef;
            //连续播放
            if (entityRef.getLastStudyTime() != null && courseChapterStudyBo.getStudyTime() > entityRef.getLastStudyTime()) {
                entity.setTotalStudyTime(entityRef.getTotalStudyTime() + (courseChapterStudyBo.getStudyTime() - entityRef.getLastStudyTime()));
            }
            //重新开始播放
            if (entityRef.getLastStudyTime() != null && courseChapterStudyBo.getStudyTime() < entityRef.getLastStudyTime()) {
                entity.setTotalStudyTime(entityRef.getTotalStudyTime() + courseChapterStudyBo.getStudyTime());
            }
        }
        entity.setLastStudyTime(courseChapterStudyBo.getStudyTime());
        entity.setLastTime(new Date());
        courseChapterUserStatJpaRepo.save(entity);
    }

    public CourseChapterStudyDetailBo getChapterStudy(String userId, String chapterId){
        CourseChapterUserStatEntity entity = courseChapterUserStatJpaRepo.findByUserIdAndChapterId(userId, chapterId);
        if (entity == null){
            return null;
        }
        CourseChapterStudyDetailBo result = new CourseChapterStudyDetailBo();
        BeanUtils.copyProperties(entity, result);
        return result;
    }

    private void verify(CourseChapterStudyBo courseChapterStudyBo){
        if (StringUtils.isEmpty(courseChapterStudyBo.getChapterId()) || StringUtils.isEmpty(courseChapterStudyBo.getStudyTime())){
            throw new BusinessException("参数异常");
        }
    }
}
