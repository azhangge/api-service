package com.huajie.educomponent.course.service;

import com.huajie.appbase.BaseRetMessage;
import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.bo.CourseCommentBo;
import com.huajie.educomponent.course.dao.CourseBasicJpaRepo;
import com.huajie.educomponent.course.dao.CourseCommentJpaRepo;
import com.huajie.educomponent.course.entity.CourseBasicEntity;
import com.huajie.educomponent.course.entity.CourseCommentEntity;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.service.FileStorageService;
import com.huajie.educomponent.usercenter.bo.UserBasicInfoBo;
import com.huajie.educomponent.usercenter.service.UserBasicInfoService;
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
public class CourseCommentService {

    @Autowired
    private CourseCommentJpaRepo courseCommentJpaRepo;

    @Autowired
    private CourseBasicJpaRepo courseBasicJpaRepo;

    @Autowired
    private UserBasicInfoService userBasicInfoService;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 新建评价、评价不允许更新
     * @return
     */
    public CourseCommentBo createUserComment(String userId, CourseCommentBo commentBo) {
        UserBasicInfoBo userBasicInfoBo = userBasicInfoService.getUserBasicInfo(userId);
        if (userId == null){
            throw new BusinessException("用户不存在或token已过期");
        }
        isCourseExit(commentBo.getCourseId());
        isCanComment(commentBo.getCourseId(), userId);
        CourseCommentEntity courseCommentEntity = convertToEntity(commentBo);
        courseCommentEntity.updateCreateInfo(userId);
        courseCommentEntity.updateModifyInfo(userId);
        CourseCommentBo courseCommentBo = convertToBo(courseCommentJpaRepo.save(courseCommentEntity));
        courseCommentBo.setUserName(userBasicInfoBo.getNickName());
        return courseCommentBo;
    }

    /**
     * 课程评价查询
     * @param courseId
     * @param page
     * @param size
     * @return
     */
    public PageResult<CourseCommentBo> search(String courseId, int page, int size){
        PageResult<CourseCommentEntity> listPages = courseCommentJpaRepo.listPages(courseId, page, size);
        PageResult<CourseCommentBo> result = new PageResult<CourseCommentBo>();
        List<CourseCommentBo> courseCommentBos = new ArrayList<CourseCommentBo>();
        for (CourseCommentEntity courseCommentEntity :listPages.getItems()){
            CourseCommentBo courseCommentBo = convertToBo(courseCommentEntity);
            UserBasicInfoBo user = userBasicInfoService.getUserBasicInfo(courseCommentEntity.getCreatorId());
            courseCommentBo.setUserName(user.getNickName());
            if (!StringUtils.isEmpty(user.getIcon())) {
                FileStorageBo icon = fileStorageService.getStore(user.getIcon());
                courseCommentBo.setUserFilePath(PathUtils.getFilePath(icon));
            }
            courseCommentBos.add(courseCommentBo);
        }
        result.setTotal(listPages.getTotal());
        result.setItems(courseCommentBos);
        return result;
    }

    /**
     * 课程是否存在
     * @param courseId
     * @throws Exception
     */
    private void isCourseExit(String courseId) throws RuntimeException {
        CourseBasicEntity courseBasicEntity = courseBasicJpaRepo.findOne(courseId);
        if (courseBasicEntity == null){
            throw new RuntimeException(BaseRetMessage.COURSE_NOT_EXIST.getValue());
        }
    }

    /**
     * 是否能够评价
     * @param courseId
     * @param userId
     * @throws Exception
     */
    private void isCanComment(String courseId, String userId) {
        CourseCommentEntity userComment = courseCommentJpaRepo.findByCourseIdAndUserId(courseId, userId);
        if (userComment != null){
            throw new BusinessException(BaseRetMessage.HAVED_COMMENT.getValue());
        }
    }

    /**
     * toBo
     * @param entity
     * @return
     */
    private CourseCommentBo convertToBo(CourseCommentEntity entity){
        if (entity == null){
            return null;
        }
        CourseCommentBo bo = new CourseCommentBo();
        BeanUtils.copyProperties(entity, bo);
        if (entity.getCreatorId() != null){
            UserBasicInfoBo user = userBasicInfoService.getUserBasicInfo(entity.getCreatorId());
            if (!StringUtils.isEmpty(user.getIcon())) {
                FileStorageBo file = fileStorageService.getStore(user.getIcon());
                bo.setUserFilePath(PathUtils.getFilePath(file));
            }
        }
        return bo;
    }

    /**
     * toEntity
     * @param courseCommentBo
     * @return
     */
    private CourseCommentEntity convertToEntity(CourseCommentBo courseCommentBo){
        if (courseCommentBo == null){
            return null;
        }
        CourseCommentEntity courseCommentEntity = new CourseCommentEntity();
        BeanUtils.copyProperties(courseCommentBo, courseCommentEntity);
        return courseCommentEntity;
    }
}
