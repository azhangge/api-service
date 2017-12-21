package com.huajie.educomponent.course.service;

import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.bo.*;
import com.huajie.educomponent.course.constants.CourseApproveStatusType;
import com.huajie.educomponent.course.constants.CourseSortType;
import com.huajie.educomponent.pubrefer.constants.UserOperateType;
import com.huajie.educomponent.course.dao.*;
import com.huajie.educomponent.course.entity.*;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.dao.UserOperateJpaRepo;
import com.huajie.educomponent.pubrefer.entity.FileStorageEntity;
import com.huajie.educomponent.pubrefer.entity.UserOperateEntity;
import com.huajie.educomponent.pubrefer.service.FileStorageService;
import com.huajie.educomponent.usercenter.bo.TeacherBriefBo;
import com.huajie.educomponent.usercenter.bo.UserRealInfoBo;
import com.huajie.educomponent.usercenter.service.TeacherService;
import com.huajie.educomponent.usercenter.service.UserRealInfoService;
import com.huajie.utils.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * Created by fangxing on 17-7-11.
 */
@Service
public class CourseService {

    @Autowired
    private CourseBasicJpaRepo courseBasicJpaRepo;

    @Autowired
    private CourseChapterJpaRepo courseChapterJpaRepo;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CourseAttachmentJpaRepo courseAttachmentJpaRepo;

    @Autowired
    private UserOperateJpaRepo userOperateJpaRepo;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserRealInfoService userRealInfoService;

    @Autowired
    private CourseQuestionSetService courseQuestionSetService;

    /**
     * 新增课程
     *
     * @param courseBo
     * @return
     */
    public CourseCreateBo saveCourse(String userId, String isWithRequest, CourseBo courseBo) {

        CourseBasicEntity courseBasicEntity;
        //判断是创建还是更新,更新会将所有字段都传过来
        if (courseBo.getCourseId() == null) {
            courseBasicEntity = convertBoToEntity(courseBo);
            courseBasicEntity.updateCreateInfo(userId);
            courseBasicEntity.setAccessCount(0);
        } else {
            courseBasicEntity = courseBasicJpaRepo.findOne(courseBo.getCourseId());
            if (courseBasicEntity == null) {
                throw new BusinessException("课程不存在");
            }
        }
        CourseCreateBo courseCreateBo = new CourseCreateBo();
        BeanUtils.copyProperties(courseBo, courseCreateBo);
        BeanUtils.copyProperties(courseCreateBo, courseBasicEntity);
        courseBasicEntity.updateModifyInfo(userId);
        courseBasicEntity.setIsOnShelves(false);
        //未指定课程讲师，默认讲师未创建者
        if (StringUtils.isEmpty(courseBo.getTeacherId())) {
            UserRealInfoBo userRealInfo = userRealInfoService.getUserRealInfo(userId);
            if (userRealInfo != null && !StringUtils.isEmpty(userRealInfo.getUserRealInfoId())) {
                courseBasicEntity.setTeacherId(userRealInfo.getUserRealInfoId());
            }
        }
        if (!StringUtils.isEmpty(isWithRequest) && isWithRequest.equals("isWithRequest")) {
            courseBasicEntity.setApproveStatus(CourseApproveStatusType.REQUEST.getValue());
        } else if (!StringUtils.isEmpty(isWithRequest) && isWithRequest.equals("noRequest")) {
            courseBasicEntity.setApproveStatus(CourseApproveStatusType.NEW.getValue());
        }
        //保存课程
        courseBasicEntity = courseBasicJpaRepo.save(courseBasicEntity);
        //删除章节
        if (courseBo.getDeleteIds() != null && courseBo.getDeleteIds().size() > 0) {
            for (String chapterId : courseBo.getDeleteIds()) {
                deleteChapter(userId, chapterId);
            }
        }
        //新建课程，保存章节
        if (StringUtils.isEmpty(courseBo.getCourseId()) && courseBo.getChapters() != null) {
            saveChapters(userId, courseBasicEntity.getId(), courseBo.getChapters());
        }
        //修改课程，保存章节
        if (!StringUtils.isEmpty(courseBo.getCourseId()) && courseBo.getChangeChapterBos() != null) {
            saveChapters(userId, courseBasicEntity.getId(), courseBo.getChangeChapterBos());
        }
        //设置课程练习
        courseQuestionSetService.save(courseBasicEntity.getId(),courseBo.getCourseQuestionSetBo());
        return convertToCreateBo(courseBasicEntity);
    }

    /**
     * 保存课程章节
     *
     * @param chapterBo
     * @return
     */
    public CourseChapterBo saveChapter(String userId, CourseChapterBo chapterBo) {
        CourseChapterEntity entity = saveChapterBo(userId, chapterBo);
        return convertToBo(entity);
    }

    /**
     * 保存课程附件
     *
     * @param bo
     * @return
     */
    public CourseAttachmentBo saveAttachment(CourseAttachmentBo bo) {

        CourseAttachmentEntity courseAttachmentEntity = courseAttachmentJpaRepo.findByCourseId(bo.getCourseId());
        if (courseAttachmentEntity == null) {
            courseAttachmentEntity = new CourseAttachmentEntity();
        }
        BeanUtils.copyProperties(bo, courseAttachmentEntity);
        CourseAttachmentEntity result = courseAttachmentJpaRepo.save(courseAttachmentEntity);
        return convertToBo(result);
    }

    /**
     * 查询课程详情
     *
     * @param courseId
     * @return
     */
    public CourseBo getDetail(String courseId, String userId) {

        CourseBasicEntity briefEntity = courseBasicJpaRepo.findById(courseId);
        if (briefEntity == null) {
            throw new BusinessException("课程不存在");
        }
        CourseBo courseBo = new CourseBo();
        CourseBasicBo courseBasicBo = convertToBo(briefEntity);
        BeanUtils.copyProperties(courseBasicBo, courseBo);
        //讲师名称
        if (!StringUtils.isEmpty(briefEntity.getTeacherId())) {
            UserRealInfoBo userRealInfo = userRealInfoService.getRealById(briefEntity.getTeacherId());
            if (userRealInfo != null) {
                courseBo.setTeacherName(userRealInfo.getRealName());
            }
        }
        //附件
        CourseAttachmentEntity courseAttachmentEntity = courseAttachmentJpaRepo.findByCourseId(courseId);
        if (courseAttachmentEntity != null && courseAttachmentEntity.getCourseAttachmentId() != null) {
            courseBo.setCourseAttachmentId(courseAttachmentEntity.getCourseAttachmentId());
            FileStorageBo fileBo = fileStorageService.getStore(courseAttachmentEntity.getCourseAttachmentId());
            courseBo.setCourseAttachmentPath(PathUtils.getHtmlVisitPath(fileBo));
            int attachmentLen = fileStorageService.getVideoLen(courseAttachmentEntity.getCourseAttachmentId());
            courseBo.setCourseSecond(courseBo.getCourseSecond() + attachmentLen);
            if (fileBo != null && !StringUtils.isEmpty(fileBo.getFileName())){
                courseBo.setCourseAttachmentName(fileBo.getFileName());
            }
        }
        //章节
        courseBo.setCourseSecond(0);
        courseBo.setChapters(getChapters(courseId, courseBo));
        //封面路径
        if (briefEntity.getThumbnailId() != null) {
            FileStorageBo fileStorage = fileStorageService.getStore(briefEntity.getThumbnailId());
            courseBo.setThumbnailPath(PathUtils.getFilePath(fileStorage));
        }
        //用户课程关系
        if (userId != null) {
            List<UserOperateEntity> userCourseEntities = userOperateJpaRepo.findByCourseId(courseId, userId);
            for (UserOperateEntity userCourse : userCourseEntities) {
                if (UserOperateType.FAVORITE_COURSE.getValue() == userCourse.getType()) {
                    courseBo.setIsFavorite(1);
                }
                if (UserOperateType.BUY.getValue() == userCourse.getType()) {
                    courseBo.setIsBuy(1);
                }
                if (UserOperateType.JOIN_IN.getValue() == userCourse.getType()) {
                    courseBo.setIsJoin(1);
                }
            }
        }

        return courseBo;
    }

    /**
     * 讲师课程查询
     *
     * @param userId
     * @param approveStatus
     * @return
     */
    public List<CourseBasicBo> search(String userId, Integer approveStatus) {
        List<CourseBasicEntity> basicEntityList;
        if (approveStatus != null) {
            basicEntityList = courseBasicJpaRepo.findByCreatorIdAndStatus(userId, approveStatus);
        } else {
            basicEntityList = courseBasicJpaRepo.findByCreatorId(userId);
        }
        List<CourseBasicBo> courseBriefBos = new ArrayList<CourseBasicBo>();
        for (CourseBasicEntity courseBasicEntity : basicEntityList) {
            CourseBasicBo courseBriefBo = convertToBo(courseBasicEntity);
            if (courseBasicEntity.getThumbnailId() != null) {
                FileStorageBo fileStorage = fileStorageService.getStore(courseBasicEntity.getThumbnailId());
                courseBriefBo.setThumbnailPath(PathUtils.getFilePath(fileStorage));
            }
            courseBriefBos.add(courseBriefBo);
        }
        return courseBriefBos;
    }


    /**
     * 删除课程
     *
     * @param userId, courseId
     * @throws Exception
     */
    public void delete(String userId, List<String> courseIds) throws RuntimeException {

        List<CourseBasicEntity> basicEntityList = courseBasicJpaRepo.findByIdIn(courseIds);
        for (CourseBasicEntity basicEntity : basicEntityList) {
            if (basicEntity.getIsOnShelves() != null && basicEntity.getIsOnShelves() == true) {
                throw new BusinessException("课程已上架，不能删除");
            }
            //删除课程的章节及上传的资料
            List<CourseChapterEntity> courseChapters = courseChapterJpaRepo.findByCourseId(basicEntity.getId());
            for (CourseChapterEntity chapter : courseChapters) {
                if (!StringUtils.isEmpty(chapter.getResourceId())) {
                    fileStorageService.deleteFile(chapter.getResourceId());
                    chapter.setDeleted(true);
                    courseChapterJpaRepo.save(chapter);
                }
            }
            //删除附件
            CourseAttachmentEntity courseAttachment = courseAttachmentJpaRepo.findByCourseId(basicEntity.getId());
            if (courseAttachment != null) {
                fileStorageService.deleteFile(courseAttachment.getCourseAttachmentId());
            }
            basicEntity.setDeleted(true);
            courseBasicJpaRepo.save(basicEntity);
        }

    }

    /**
     * 删除课程章节
     *
     * @param userId, chapterId
     * @throws Exception
     */
    public void deleteChapter(String userId, String chapterId) {

        CourseChapterEntity chapterEntity = courseChapterJpaRepo.findById(chapterId);
        if (chapterEntity == null) {
            throw new BusinessException("章节不存在！");
        }
        chapterEntity.setDeleted(true);
        chapterEntity.updateModifyInfo(userId);
        courseChapterJpaRepo.save(chapterEntity);
    }

    /**
     * 撤回申请
     *
     * @param userId
     * @param courseIds
     * @throws Exception
     */
    public void rollback(String userId, List<String> courseIds) {

        List<CourseBasicEntity> courseList = courseBasicJpaRepo.findByIdIn(courseIds);
        for (CourseBasicEntity basicEntity : courseList) {
            if (basicEntity.getApproveStatus() == null || basicEntity.getApproveStatus() != 2) {
                throw new BusinessException("操作错误");
            }
            basicEntity.setApproveStatus(1);
            basicEntity.updateModifyInfo(userId);
            courseBasicJpaRepo.save(basicEntity);
        }
    }

    /**
     * 上架\下架
     *
     * @param userId
     * @param courseIds
     * @throws Exception
     */
    public void shelve(String userId, List<String> courseIds) throws RuntimeException {
        List<CourseBasicEntity> courseList = courseBasicJpaRepo.findByIdIn(courseIds);
        for (CourseBasicEntity basicEntity : courseList) {
            if (basicEntity.getIsOnShelves() == false) {
                basicEntity.setIsOnShelves(true);
            } else {
                basicEntity.setIsOnShelves(false);
            }
            courseBasicJpaRepo.save(basicEntity);
        }
    }

    /**
     * 按照最热返回推荐课程
     *
     * @return
     */
    public List<CourseBasicBo> getRecommend() {
        List<CourseBasicEntity> courseBasicEntities = courseBasicJpaRepo.getRecommend();
        List<CourseBasicBo> recommendCourse = new ArrayList<CourseBasicBo>();
        for (CourseBasicEntity courseBasicEntity : courseBasicEntities) {
            CourseBasicBo courseBasicBo = convertToBo(courseBasicEntity);
            recommendCourse.add(courseBasicBo);
        }
        return recommendCourse;
    }

    /**
     * 查询课程
     *
     * @param mainCategoryId
     * @param subCategoryId
     * @param orderKey
     * @param order
     * @param maxScore
     * @param minScore
     * @param queryKeyword
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageResult<CourseBasicBo> courseList(String queryKeyword, String mainCategoryId, String subCategoryId, Boolean isPublic, Boolean onShelves,
                                                Integer orderKey, Integer order, Integer maxScore, Integer minScore, int pageIndex, int pageSize) {

        //Todo 参数改成Map
        PageResult<CourseBasicBo> result = new PageResult<CourseBasicBo>();
        List<CourseBasicBo> basicBos = new ArrayList<CourseBasicBo>();
        PageResult<CourseBasicEntity> basicResult = courseBasicJpaRepo.listPages(queryKeyword, mainCategoryId, subCategoryId, isPublic, onShelves, orderKey, order, maxScore, minScore, pageIndex, pageSize);
        for (CourseBasicEntity entity : basicResult.getItems()) {
            CourseBasicBo courseBasicBo = convertToBo(entity);
            basicBos.add(courseBasicBo);
        }
        result.setTotal(basicResult.getTotal());
        result.setItems(basicBos);
        return result;
    }

    /**
     * 获取最新、最热课程
     *
     * @return
     */
    public PageResult<CourseBasicBo> getCourseSort(Integer sortType, int pageIndex, int pageSize) {

        if (!(sortType.equals(CourseSortType.NEWEST.getValue()) || sortType.equals(CourseSortType.HOTEST.getValue()))) {
            return null;
        }
        PageResult<CourseBasicBo> result = new PageResult<CourseBasicBo>();
        PageResult<CourseBasicEntity> courses;
        if (sortType == CourseSortType.NEWEST.getValue()) {
            courses = courseBasicJpaRepo.listPages(null, null, null, true, true, 0, 1, null, null, pageIndex, pageSize);
        } else {
            courses = courseBasicJpaRepo.listPages(null, null, null, true, true, 1, 1, null, null, pageIndex, pageSize);
        }
        List<CourseBasicBo> basicBoList = convertToBoList((List<CourseBasicEntity>) courses.getItems());
        result.setItems(basicBoList);
        result.setTotal(courses.getTotal());
        return result;
    }

    /**
     * 获取某个分类下课程学习人数最多的课程
     *
     * @param categoryType
     * @param categoryId
     * @return
     */
    public CourseBasicBo getCategoryCourseTop(Integer categoryType, String categoryId) {
        CourseBasicEntity courseBasic = courseBasicJpaRepo.findCategoryCourseTop(categoryType, categoryId);
        return convertToBo(courseBasic);
    }

    /**
     * 对外模块，查询
     *
     * @param courseId
     * @return
     */
    public CourseBasicEntity getCourseBrief(String courseId) {
        return courseBasicJpaRepo.findById(courseId);
    }

    /**
     * 对外模块，保存
     *
     * @param courseBasicEntity
     * @return
     */
    public CourseBasicEntity svaeCourseBrief(CourseBasicEntity courseBasicEntity) {
        return courseBasicJpaRepo.save(courseBasicEntity);
    }

    /**
     * toBo(toBo外部会调用)
     *
     * @param courseBasicEntity
     * @return
     */
    public CourseBasicBo convertToBo(CourseBasicEntity courseBasicEntity) {
        if (courseBasicEntity == null) {
            return null;
        }
        CourseBasicBo courseBasicBo = new CourseBasicBo();
        BeanUtils.copyProperties(courseBasicEntity, courseBasicBo);
        courseBasicBo.setCourseId(courseBasicEntity.getId());
        if (!StringUtils.isEmpty(courseBasicEntity.getThumbnailId())) {
            FileStorageBo file = fileStorageService.getStore(courseBasicEntity.getThumbnailId());
            courseBasicBo.setThumbnailPath(PathUtils.getFilePath(file));
        }
        //讲师
        if (!StringUtils.isEmpty(courseBasicEntity.getTeacherId())) {
            TeacherBriefBo teacherBriefBo = teacherService.getTeacherById(courseBasicEntity.getTeacherId());
            if (teacherBriefBo != null && !StringUtils.isEmpty(teacherBriefBo.getRealName())) {
                courseBasicBo.setTeacherName(teacherBriefBo.getRealName());
            }
        }
        return courseBasicBo;
    }

    public List<CourseBasicBo> convertToBoList(List<CourseBasicEntity> courseBasicEntities) {
        List<CourseBasicBo> courseBasicBos = new ArrayList<CourseBasicBo>();
        for (CourseBasicEntity courseBasicEntity : courseBasicEntities) {
            CourseBasicBo courseBasicBo = convertToBo(courseBasicEntity);
            courseBasicBos.add(courseBasicBo);
        }
        return courseBasicBos;
    }


    private List<CourseChapterBo> getChapters(String courseId, CourseBo courseBo) {
        List<CourseChapterEntity> chapterEntities = courseChapterJpaRepo.findByCourseIdAndDeleted(courseId);
        if (chapterEntities == null || chapterEntities.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        List<CourseChapterBo> chapterBos = new ArrayList<CourseChapterBo>();
        for (CourseChapterEntity categoryEntity : chapterEntities) {
            CourseChapterBo bo = convertToBo(categoryEntity);
            chapterBos.add(bo);
        }
        List<CourseChapterBo> root = new ArrayList<CourseChapterBo>();
        for (CourseChapterBo chapter : chapterBos) {
            if (StringUtils.isEmpty(chapter.getParentId())) {
                root.add(chapter);
                courseBo.setCourseSecond(courseBo.getCourseSecond() + chapter.getSecond());
            }
            List<CourseChapterBo> children = new ArrayList<CourseChapterBo>();
            for (CourseChapterBo item : chapterBos) {
                if (StringUtils.isEmpty(item.getParentId()) != true
                        && item.getParentId().equals(chapter.getChapterId())) {
                    if (item.getChildren() == null) {
                        children.add(item);
                        chapter.setChildren(children);
                    } else {
                        chapter.getChildren().add(item);
                    }
                    courseBo.setCourseSecond(courseBo.getCourseSecond() + item.getSecond());
                }
            }
        }
        return root;
    }

    private CourseChapterBo convertToBo(CourseChapterEntity entity) {
        CourseChapterBo bo = new CourseChapterBo();
        if (entity != null) {
            BeanUtils.copyProperties(entity, bo);
            bo.setChapterId(entity.getId());
        }
        if (bo.getResourceId() != null) {
            FileStorageBo fileStorageBo = fileStorageService.getStore(bo.getResourceId());
            bo.setResourcePath(PathUtils.getFilePath(fileStorageBo));
            bo.setResourceHtmlPath(PathUtils.getHtmlVisitPath(fileStorageBo));
            int chapterLen = fileStorageService.getVideoLen(bo.getResourceId());
            bo.setSecond(chapterLen);
            if (fileStorageBo != null){
                bo.setFileSize(fileStorageBo.getFileSize());
            }
        }
        return bo;
    }

    private CourseAttachmentBo convertToBo(CourseAttachmentEntity entity) {

        if (entity != null) {
            CourseAttachmentBo bo = new CourseAttachmentBo();
            BeanUtils.copyProperties(entity, bo);
            if (bo.getCourseAttachmentId() != null) {
                FileStorageBo file = fileStorageService.getStore(bo.getCourseAttachmentId());
                bo.setCourseAttachmentPath(PathUtils.getFilePath(file));
                FileStorageEntity fileStorageEntity = new FileStorageEntity();
                BeanUtils.copyProperties(file, fileStorageEntity);
                fileStorageEntity.setId(file.getFileId());
                if (!StringUtils.isEmpty(entity.getCourseId())) {
                    fileStorageEntity.setOwnerId(entity.getCourseId());
                }
                fileStorageService.saveStore(fileStorageEntity);
            }
            bo.setAttachmentId(entity.getId());
            return bo;
        }
        return null;
    }

    private void saveChapters(String userId, String courseId, List<CourseChapterBo> chapterBos) {
        for (CourseChapterBo chapterBo : chapterBos) {
            chapterBo.setCourseId(courseId);
            saveChapterBo(userId, chapterBo);
        }
    }

    private CourseChapterEntity saveChapterBo(String userId, CourseChapterBo chapterBo) {
        CourseChapterEntity courseChapterEntity;
        if (chapterBo.getChapterId() == null) {
            courseChapterEntity = new CourseChapterEntity();
            courseChapterEntity.updateCreateInfo(userId);
        } else {
            courseChapterEntity = courseChapterJpaRepo.findOne(chapterBo.getChapterId());
            if (courseChapterEntity == null) {
                throw new BusinessException("章节不存在");
            }
        }

        courseChapterEntity.updateModifyInfo(userId);
        BeanUtils.copyProperties(chapterBo, courseChapterEntity);
        courseChapterEntity = courseChapterJpaRepo.save(courseChapterEntity);

        List<CourseChapterBo> sections = chapterBo.getChildren();
        if (sections != null && sections.size() > 0) {
            for (CourseChapterBo sectionBo : sections) {
                sectionBo.setCourseId(courseChapterEntity.getCourseId());
                sectionBo.setParentId(courseChapterEntity.getId());
                saveChapterBo(userId, sectionBo);
            }
        }

        return courseChapterEntity;
    }


    private CourseBasicEntity convertBoToEntity(CourseBasicBo basicBo) {
        CourseBasicEntity courseBasicEntity = new CourseBasicEntity();
        BeanUtils.copyProperties(basicBo, courseBasicEntity);
        courseBasicEntity.setId(basicBo.getCourseId());
        return courseBasicEntity;
    }

    private CourseCreateBo convertToCreateBo(CourseBasicEntity entity) {
        if (entity == null) {
            return null;
        }
        CourseCreateBo bo = new CourseCreateBo();
        BeanUtils.copyProperties(entity, bo);
        bo.setCourseId(entity.getId());
        return bo;
    }


}
