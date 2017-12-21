package com.huajie.educomponent.portal.service;

import com.huajie.appbase.BaseRetMessage;
import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.dao.CourseBasicJpaRepo;
import com.huajie.educomponent.course.entity.CourseBasicEntity;
import com.huajie.educomponent.exam.dao.ExamNoticeJpaRepo;
import com.huajie.educomponent.exam.entity.ExamNoticeEntity;
import com.huajie.educomponent.portal.bo.ResourceBo;
import com.huajie.educomponent.portal.constants.ResourceType;
import com.huajie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourceService {

    @Autowired
    private CourseBasicJpaRepo courseBasicJpaRepo;

    @Autowired
    private ExamNoticeJpaRepo examNoticeJpaRepo;

    public PageResult<ResourceBo> getCourses(String search, Boolean isPublic, Boolean isOnShelves,int pageIndex, int pageSize) {

        PageResult<ResourceBo> pageResult = new PageResult<ResourceBo>();
        Map<String, Object> param = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(search)){
            param.put("search",search);
        }
        if(isPublic != null){
            param.put("isPublic",isPublic);
        }
        if(isOnShelves != null){
            param.put("isOnShelves",isOnShelves);
        }

        PageResult<CourseBasicEntity> courseBasicEntityPageResult = courseBasicJpaRepo.findByCondition(param, pageIndex, pageSize);
        List<CourseBasicEntity> courseBasicEntities = (List<CourseBasicEntity>)courseBasicEntityPageResult.getItems();
        if(!courseBasicEntities.isEmpty()) {
            List<ResourceBo> resourceBos = new ArrayList<ResourceBo>();
            for(CourseBasicEntity courseBasicEntity : courseBasicEntities) {
                ResourceBo resourceBo = new ResourceBo();
                resourceBo.setResourceId(courseBasicEntity.getId());
                resourceBo.setResourceType(ResourceType.COURSE.getValue());
                resourceBo.setResourceName(courseBasicEntity.getCourseName());
                resourceBos.add(resourceBo);
            }
            pageResult.setItems(resourceBos);
            pageResult.setTotal(courseBasicEntityPageResult.getTotal());
        }
        return pageResult;
    }

    public PageResult<ResourceBo> getExamNotices(String search, Boolean isPublic, Boolean isPublish,int pageIndex, int pageSize) {

        PageResult<ResourceBo> pageResult = new PageResult<ResourceBo>();
        Map<String, Object> param = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(search)){
            param.put("search",search);
        }
        if(isPublic != null){
            param.put("isPublic",isPublic);
        }
        if(isPublish != null){
            param.put("isPublish",isPublish);
        }

        PageResult<ExamNoticeEntity> courseBasicEntityPageResult = examNoticeJpaRepo.getExamNoticesByCondition(param, pageIndex, pageSize);
        List<ExamNoticeEntity> examNoticeEntities = (List<ExamNoticeEntity>)courseBasicEntityPageResult.getItems();
        if(!examNoticeEntities.isEmpty()) {
            List<ResourceBo> resourceBos = new ArrayList<ResourceBo>();
            for(ExamNoticeEntity examNoticeEntity : examNoticeEntities) {
                ResourceBo resourceBo = new ResourceBo();
                resourceBo.setResourceId(examNoticeEntity.getId());
                resourceBo.setResourceType(ResourceType.NOTICE.getValue());
                resourceBo.setResourceName(examNoticeEntity.getNoticeName());
                resourceBos.add(resourceBo);
            }
            pageResult.setItems(resourceBos);
            pageResult.setTotal(courseBasicEntityPageResult.getTotal());
        }
        return pageResult;
    }
}
