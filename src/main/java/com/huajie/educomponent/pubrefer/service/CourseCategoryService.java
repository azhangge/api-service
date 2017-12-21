package com.huajie.educomponent.pubrefer.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.dubbo.common.json.JSON;
import com.huajie.appbase.BusinessException;
import com.huajie.configs.EnvConstants;
import com.huajie.educomponent.course.bo.CourseBasicBo;
import com.huajie.educomponent.course.service.CourseService;
import com.huajie.educomponent.portal.bo.PortalBriefCourseBo;
import com.huajie.educomponent.pubrefer.bo.CourseCategoryNodeBo;
import com.huajie.educomponent.pubrefer.bo.CourseCategoryTreeNodeBo;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.bo.CourseCategoryHotBo;
import com.huajie.educomponent.pubrefer.dao.CourseCategoryJpaRepo;
import com.huajie.educomponent.pubrefer.entity.CourseCategoryEntity;
import com.huajie.utils.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * Created by fangxing on 17-7-6.
 */
@Service
public class CourseCategoryService {

    @Autowired
    private CourseCategoryJpaRepo courseCategoryJpaRepo;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CourseService courseService;

    /**
     * 根据id获取分类
     * @param id
     * @return
     */
    public CourseCategoryEntity findById(String id) {
        return courseCategoryJpaRepo.findById(id);
    }

    /**
     * 根据code获取分类
     * @param code
     * @return
     */
    public CourseCategoryEntity findByCode(String code) {
        return courseCategoryJpaRepo.findById(code);
    }

    /**
     * 新建、更新分类
     * @param nodeBo
     * @return
     */
    public CourseCategoryNodeBo save(String userId, CourseCategoryNodeBo nodeBo) {
        Date now = new Date();
        CourseCategoryEntity entity = null;
        if (nodeBo.getCourseCategoryId() == null) {
            entity = new CourseCategoryEntity();
            entity.setCreatorId(userId);
            entity.setCreateDate(now);
        } else {
            entity = courseCategoryJpaRepo.findById(nodeBo.getCourseCategoryId());
        }

        entity.setModifierId(userId);
        entity.setModifyDate(now);

        BeanUtils.copyProperties(nodeBo, entity);
        entity = courseCategoryJpaRepo.save(entity);

        CourseCategoryNodeBo categoryNodeBo = convertToBo(entity);

        return categoryNodeBo;
    }

    public Boolean saveBatch(String userId, List<CourseCategoryNodeBo> nodeBos) {
        List<CourseCategoryNodeBo> parentNodes = new ArrayList<CourseCategoryNodeBo>();
        List<CourseCategoryNodeBo> subNodes = new ArrayList<CourseCategoryNodeBo>();

        for (CourseCategoryNodeBo bo : nodeBos) {
            if (StringUtils.isEmpty(bo.getParentId())) {
                parentNodes.add(bo);
            } else {
                subNodes.add(bo);
            }
        }

        String idStr = null;
        for (CourseCategoryNodeBo bo : parentNodes) {
            Boolean isNew = false;
            if (isTemporaryId(bo.getCourseCategoryId())) {
                idStr = new String(bo.getCourseCategoryId());
                bo.setCourseCategoryId(null);
                isNew = true;
            }
            CourseCategoryNodeBo nodeBo = save(userId, bo);

            if (isNew == true) {
                for (CourseCategoryNodeBo item: subNodes) {
                    if (item.getParentId().equals(idStr)) {
                        item.setParentId(nodeBo.getCourseCategoryId());
                    }
                }
            }
        }

        for (CourseCategoryNodeBo bo : subNodes) {
            if (isTemporaryId(bo.getCourseCategoryId())) {
                bo.setCourseCategoryId(null);
            }

            save(userId, bo);
        }

        return true;
    }

    public Boolean deleteBatch(String userId, List<String> ids){
        Date now = new Date();
        List<CourseCategoryEntity> entities = courseCategoryJpaRepo.findAll(ids);
        for (CourseCategoryEntity entity : entities) {
            entity.setDeleted(true);
            entity.setModifierId(userId);
            entity.setModifyDate(now);
        }
        courseCategoryJpaRepo.save(entities);

        return true;
    }

    private Boolean isTemporaryId(String id) {
        if (true == StringUtils.isEmpty(id)) {
            return true;
        }

        if (id.indexOf(EnvConstants.TEMP_ID_PREFIX) == -1) {
            return false;
        }

        return true;
    }

    /**
     * 获取所有的分类(树形结构)
     * @return
     */
    public List<CourseCategoryTreeNodeBo> getCourseCategoryTree() {

        List<CourseCategoryEntity> entities = courseCategoryJpaRepo.findAllByDeleted(false);

        List<CourseCategoryTreeNodeBo> temp = new ArrayList<CourseCategoryTreeNodeBo>();
        for (CourseCategoryEntity categoryEntity:entities){
            CourseCategoryTreeNodeBo bo = convertToTreeBo(categoryEntity);
            temp.add(bo);
        }
        List<CourseCategoryTreeNodeBo> root = new ArrayList<CourseCategoryTreeNodeBo>();
        for (CourseCategoryTreeNodeBo courseCategory:temp){
            if (courseCategory.getParentId() == null){
                root.add(courseCategory);
            }
            List<CourseCategoryTreeNodeBo> children = new ArrayList<CourseCategoryTreeNodeBo>();
            for (CourseCategoryTreeNodeBo category:temp){
                if (category.getParentId() != null && category.getParentId().equals(courseCategory.getCourseCategoryId())){
                    if (category.getChildren() == null){
                        children.add(category);
                        courseCategory.setChildren(children);
                    }else {
                        courseCategory.getChildren().add(category);
                    }
                }
            }
        }
        return root;
    }


    /**
     * 删除课程分类
     * @param userId
     * @param categoryId
     */
    public void delete(String userId, String categoryId) {
        CourseCategoryEntity courseCategoryEntity = courseCategoryJpaRepo.findOne(categoryId);
        if (courseCategoryEntity == null){
            throw new BusinessException("资源不存在");
        }
        //该分类下有课程、不能删除
        CourseBasicBo courseMain = courseService.getCategoryCourseTop(1, categoryId);
        CourseBasicBo courseSub = courseService.getCategoryCourseTop(2, categoryId);
        if (courseMain != null || courseSub != null){
            throw new BusinessException("该类已使用、不能删除");
        }
        //主类子类使用情况
        CourseCategoryTreeNodeBo tree = getSubCategory(categoryId);
        for (CourseCategoryTreeNodeBo CourseCategoryTreeNodeBo:tree.getChildren()){
            CourseBasicBo course = courseService.getCategoryCourseTop(2, CourseCategoryTreeNodeBo.getCourseCategoryId());
            if (courseSub != null){
                throw new BusinessException("该主类下子类已使用、不能删除");
            }
            //未使用，主类下子类都删除
            CourseCategoryEntity courseCategory = courseCategoryJpaRepo.findOne(CourseCategoryTreeNodeBo.getCourseCategoryId());
            courseCategory.setDeleted(true);
            courseCategoryJpaRepo.save(courseCategory);
        }
        courseService.getCategoryCourseTop(1, categoryId);
        courseCategoryEntity.setDeleted(true);
        courseCategoryJpaRepo.save(courseCategoryEntity);
    }

    /**
     * 获取最热类别(主类别和次类别),数量可控制
     * @return
     */
    public CourseCategoryHotBo getHotCategoryList(int mainCount, int subCount){

        List<CourseCategoryNodeBo> mainCategory = new ArrayList<CourseCategoryNodeBo>();
        List<CourseCategoryNodeBo> subCategory = new ArrayList<CourseCategoryNodeBo>();
        //获取课程学习人数最大的分类（未排序）
        List<CourseBasicBo> parentTOP = new ArrayList<CourseBasicBo>();
        List<CourseBasicBo> childrenTOP = new ArrayList<CourseBasicBo>();
        List<CourseCategoryTreeNodeBo> categoryTree = getCourseCategoryTree();
        for (CourseCategoryTreeNodeBo parent:categoryTree){
            CourseBasicBo parentBo = courseService.getCategoryCourseTop(1, parent.getCourseCategoryId());
            if (parentBo == null){
                continue;
            }
            if (!StringUtils.isEmpty(parentBo.getCourseId())) {
                parentTOP.add(parentBo);
            }
            if (parent.getChildren() != null) {
                for (CourseCategoryTreeNodeBo children : parent.getChildren()) {
                    CourseBasicBo childrenBo = courseService.getCategoryCourseTop(2, children.getCourseCategoryId());
                    if (childrenBo != null && !StringUtils.isEmpty(childrenBo.getCourseId())) {
                        childrenTOP.add(childrenBo);
                    }
                }
            }
        }
        //排序，获得对应数量的主次分类的课程
        List<CourseBasicBo> parentSortTOP = sort(parentTOP, mainCount, 1);
        List<CourseBasicBo> childrenSortTOP = sort(childrenTOP, subCount, 2);
        //根据最热分类查询分类详情，包装数据
        for (CourseBasicBo courseBasicBo:parentSortTOP){
            getCategory(courseBasicBo.getMainCategoryId(), mainCategory);
        }
        for (CourseBasicBo courseBasicBo:childrenSortTOP){
            getCategory(courseBasicBo.getSubCategoryId(), subCategory);
        }
        CourseCategoryHotBo categoryHotBo = new CourseCategoryHotBo();
        categoryHotBo.setMainCategory(mainCategory);
        categoryHotBo.setSubCategory(subCategory);
        return categoryHotBo;
    }

    /**
     * 获取某主类下子类
     * @param mainCategoryId
     * @return
     */
    public CourseCategoryTreeNodeBo getSubCategory(String mainCategoryId){
        List<CourseCategoryEntity> sub = courseCategoryJpaRepo.findAllByMain(mainCategoryId);
        CourseCategoryEntity main = courseCategoryJpaRepo.findOne(mainCategoryId);
        CourseCategoryTreeNodeBo result = convertToTreeBo(main);
        List<CourseCategoryTreeNodeBo> children = new ArrayList<CourseCategoryTreeNodeBo>();
        for (CourseCategoryEntity entity:sub){
            children.add(convertToTreeBo(entity));
        }
        result.setChildren(children);
        return result;
    }

    private void getCategory(String categoryId, List<CourseCategoryNodeBo> category){
        CourseCategoryEntity categoryCourse = courseCategoryJpaRepo.findById(categoryId);
        if (categoryCourse == null){
            return;
        }
        CourseCategoryNodeBo mainCategoryNodeBo = convertToBo(categoryCourse);
        category.add(mainCategoryNodeBo);
    }

    /**
     * 返回数量控制
     * @param source
     * @param count
     * @return
     */
    private List<CourseBasicBo> sort(List<CourseBasicBo> source, int count, int cateType){
        Collections.sort(source, new Comparator<CourseBasicBo>() {
            @Override
            public int compare(CourseBasicBo o1, CourseBasicBo o2) {
                return o2.getAccessCount().compareTo(o1.getAccessCount());
            }
        });
        for (int i = 0; i < source.size()-1; i++) {
            for (int j = source.size()-1; j > i; j--) {
                if (cateType == 1) {
                    if (source.get(j).getMainCategoryId().equals(source.get(i).getMainCategoryId())) {
                        source.remove(j);
                    }
                }else if (cateType == 2){
                    if (source.get(j).getSubCategoryId().equals(source.get(i).getSubCategoryId())) {
                        source.remove(j);
                    }
                }
            }
        }
        if (count <= source.size()){
            source = source.subList(0, count);
        }
        return source;
    }

    /**
     * 转换为Entity
     * @param bo
     * @return
     */
    private CourseCategoryEntity convertToEntity(CourseCategoryNodeBo bo){
        CourseCategoryEntity entity = new CourseCategoryEntity();
        if (bo != null) {
            BeanUtils.copyProperties(bo, entity);
            entity.setId(bo.getCourseCategoryId());
        }
        return entity;
    }

    /**
     * 转换为bo
     * @param entity
     * @return
     */
    private CourseCategoryNodeBo convertToBo(CourseCategoryEntity entity){
        if (entity == null) {
            return null;
        }
        CourseCategoryNodeBo bo = new CourseCategoryNodeBo();
        BeanUtils.copyProperties(entity, bo);
        bo.setCourseCategoryId(entity.getId());
        if (entity.getAppIcon() != null) {
            FileStorageBo fileStorageBo = fileStorageService.getStore(bo.getAppIcon());
            bo.setAppIconPath(PathUtils.getFilePath(fileStorageBo));
        }
        return bo;
    }

    private CourseCategoryTreeNodeBo convertToTreeBo(CourseCategoryEntity entity){
        CourseCategoryTreeNodeBo bo = new CourseCategoryTreeNodeBo();
        if (entity != null) {
            BeanUtils.copyProperties(entity, bo);
            bo.setCourseCategoryId(entity.getId());
        }
        return bo;
    }


    /**
     * 查询所有一级根类目
     * @return
     */
    public List<CourseCategoryNodeBo> findAllRootCategory(){
        List<CourseCategoryEntity> categoryEntities = courseCategoryJpaRepo.findAllByDeleted(false);
        List<CourseCategoryEntity> subs = new ArrayList<CourseCategoryEntity>();
        if(categoryEntities.size()>0) {
            for (CourseCategoryEntity courseCategoryEntity:categoryEntities) {
                    if(courseCategoryEntity.getParentId() == null){
                        subs.add(courseCategoryEntity);
                    }
            }
        }
        List<CourseCategoryNodeBo> subCategories = new ArrayList<CourseCategoryNodeBo>();
        for(CourseCategoryEntity courseCategoryEntity:subs) {
            CourseCategoryNodeBo courseCategoryNodeBo = new CourseCategoryNodeBo();
            BeanUtils.copyProperties(courseCategoryEntity, courseCategoryNodeBo);
            courseCategoryNodeBo.setCourseCategoryId(courseCategoryEntity.getId());
            subCategories.add(courseCategoryNodeBo);
        }
        return  subCategories;
    }
}
