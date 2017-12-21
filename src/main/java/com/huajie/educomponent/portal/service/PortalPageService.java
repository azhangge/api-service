package com.huajie.educomponent.portal.service;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.bo.CourseBasicBo;
import com.huajie.educomponent.course.constants.IndexType;
import com.huajie.educomponent.course.service.CourseService;

import com.huajie.educomponent.portal.bo.PortalBriefCourseBo;
import com.huajie.educomponent.portal.bo.PortalCourseBo;

import com.huajie.educomponent.portal.constants.RedisKeys;
import com.huajie.educomponent.pubrefer.bo.CourseCategoryNodeBo;
import com.huajie.educomponent.pubrefer.bo.CourseCategoryTreeNodeBo;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.service.CourseCategoryService;
import com.huajie.educomponent.pubrefer.service.FileStorageService;
import com.huajie.educomponent.usercenter.bo.TeacherBriefBo;
import com.huajie.educomponent.usercenter.service.TeacherService;
import com.huajie.utils.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页数据
 * Created by wangd on 2017/9/17.
 */
@Service
public class PortalPageService {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseCategoryService categoryService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 调用最新，最热课程接口
    public PortalCourseBo getCourseData(String courseItemName,Integer courseSortType){
        PortalCourseBo courseBo = null;

        courseBo = getTopCourseFromRedis(courseItemName);
        if (courseBo != null) {
            return courseBo;
        }

        courseBo = new PortalCourseBo();
        courseBo.setCourseItemName(courseItemName);

        PageResult<CourseBasicBo> courseResult = courseService.getCourseSort(courseSortType, 0, 4);
        List<CourseBasicBo> list = (List) courseResult.getItems();

        if (list.isEmpty()) {
            return courseBo;
        }

        List<PortalBriefCourseBo> briefCourseBos = new ArrayList<PortalBriefCourseBo>();
        for (CourseBasicBo courseBasicBo : list) {
            PortalBriefCourseBo portalBriefCourseBo = new PortalBriefCourseBo();
            BeanUtils.copyProperties(courseBasicBo, portalBriefCourseBo);
            briefCourseBos.add(portalBriefCourseBo);
        }

        if (courseItemName.equals(IndexType.NEWCOURSE.getValue())) {
            courseBo.setNewestCourses(briefCourseBos);

        } else {
            courseBo.setHotCourses(briefCourseBos);

        }

        saveTopCourseFromRedis(courseItemName, courseBo);

        return courseBo;
    }

    private PortalCourseBo getTopCourseFromRedis(final String courseItemName) {
        PortalCourseBo result = redisTemplate.execute(new RedisCallback<PortalCourseBo>() {
            @Override
            public PortalCourseBo doInRedis(RedisConnection connection) {
                String key = null;
                if (courseItemName.equals(IndexType.NEWCOURSE.getValue())) {
                    key = RedisKeys.HOME_PROMPT_COURSES;
                } else {
                    key = RedisKeys.HOME_HOT_COURSES;
                }

                PortalCourseBo returnObject = null;
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(keyByte)) {
                    String valueStr = redisTemplate.getStringSerializer().deserialize(connection.get(keyByte));

                    Gson gson = new Gson();
                    returnObject = gson.fromJson(valueStr, PortalCourseBo.class);
                }

                //return null;
                return returnObject;
            }
        });

        return result;
    }

    private void saveTopCourseFromRedis(final String courseItemName, final PortalCourseBo portalCourseBo) {
        final Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) {
                String key = null;
                if (courseItemName.equals(IndexType.NEWCOURSE.getValue())) {
                    key = RedisKeys.HOME_PROMPT_COURSES;
                } else {
                    key = RedisKeys.HOME_HOT_COURSES;
                }

                String objStr = null;
                byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
                Gson gson = new Gson();
                objStr = gson.toJson(portalCourseBo, PortalCourseBo.class);
                byte[] valueByte = redisTemplate.getStringSerializer().serialize(objStr);
                connection.set(keyByte, valueByte);
                return true;
            }
        });
    }


    //首页讲师
    public List<TeacherBriefBo> getHotTeacher() {
        List<TeacherBriefBo> teacherBriefBos = teacherService.getTeacherBriefBos();

        if (teacherBriefBos.isEmpty()) {
            return new ArrayList<TeacherBriefBo>();
        }

        for (TeacherBriefBo teacherBriefBo : teacherBriefBos) {
            if (teacherBriefBo.getIntroPicId() != null) {
                FileStorageBo file = fileStorageService.getStore(teacherBriefBo.getIntroPicId());
                teacherBriefBo.setIntroPicPath(PathUtils.getFilePath(file));
            }
        }

        if (teacherBriefBos.size() > 5) {
            return teacherBriefBos.subList(0, 5);
        }

        return teacherBriefBos;

    }

    // 首页content_two 课时数据和菜单
    public List<PortalCourseBo> getCourseCategoryTree() {
        List<PortalCourseBo> categories = new ArrayList<>();

        categories = getPortalCourseFromRedis();

        if (categories.size() > 0) {
            return categories;
        }

        List<CourseCategoryNodeBo> roots = categoryService.findAllRootCategory();
        for(CourseCategoryNodeBo root : roots) {
            PortalCourseBo categoryCourse = new PortalCourseBo();
            categoryCourse.setMainCategoryName(root.getName());
            categoryCourse.setMainCategoryId(root.getCourseCategoryId());
            CourseCategoryTreeNodeBo subTree = categoryService.getSubCategory(root.getId());
            List<CourseCategoryTreeNodeBo> subs = subTree.getChildren();
            //如果有二级类目 则添加第一个二级类目的课程
            if (!subs.isEmpty()) {
                List<CourseCategoryNodeBo> courseCategoryNodeBos = buildCourseCategoryNodeBo(subs);
                categoryCourse.setSubCategories(courseCategoryNodeBos);
                List<PortalBriefCourseBo> courseBos = getPortalBriefCourseBo(courseCategoryNodeBos.get(0));
                categoryCourse.setIndexCourses(courseBos);
                categories.add(categoryCourse);
            }
        }

        saveCategoriesToRedis(categories);

        return categories;
    }

    private List<PortalCourseBo> getPortalCourseFromRedis() {
        return redisTemplate.execute(new RedisCallback<List<PortalCourseBo>>() {
            public List<PortalCourseBo> doInRedis(RedisConnection connection) {
                List<PortalCourseBo> result = new ArrayList<>();
                byte[] key = redisTemplate.getStringSerializer().serialize(RedisKeys.COURSE_CATEGORIES);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String jsonValue = redisTemplate.getStringSerializer().deserialize(value);

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<PortalCourseBo>>() {
                    }.getType();
                    result = gson.fromJson(jsonValue, type);
                }
                return result;
            }
        });
    }


    private void saveCategoriesToRedis(final List<PortalCourseBo> categories) {
        redisTemplate.execute(new RedisCallback<List<PortalCourseBo>>() {
            public List<PortalCourseBo> doInRedis(RedisConnection connection) {
                List<PortalCourseBo> result = new ArrayList<>();
                byte[] key = redisTemplate.getStringSerializer().serialize(RedisKeys.COURSE_CATEGORIES);
                Gson gson = new Gson();
                Type type = new TypeToken<List<PortalCourseBo>>() {
                }.getType();
                String valueString = gson.toJson(categories, type);
                byte[] value = redisTemplate.getStringSerializer().serialize(valueString);

                connection.set(key, value);
                return result;
            }
        });
    }

      // 构建子节点数据
     private List<CourseCategoryNodeBo> buildCourseCategoryNodeBo(List<CourseCategoryTreeNodeBo> list){
         List<CourseCategoryNodeBo> courseCategoryNodeBos = new ArrayList<CourseCategoryNodeBo>();
         for(CourseCategoryTreeNodeBo courseCategoryTreeNodeBo:list){
             CourseCategoryNodeBo courseCategoryNodeBo = new CourseCategoryNodeBo();
             BeanUtils.copyProperties(courseCategoryTreeNodeBo, courseCategoryNodeBo);
             courseCategoryNodeBos.add(courseCategoryNodeBo);
         }
        return  courseCategoryNodeBos;
     }


      // 构建index 页面课程数据
     private List<PortalBriefCourseBo> getPortalBriefCourseBo(CourseCategoryNodeBo subCategory){
         List<PortalBriefCourseBo> portalBriefCourseBos = new ArrayList<PortalBriefCourseBo>();
         PageResult<CourseBasicBo> subCategoryCourses = courseService.courseList(null, null, subCategory.getCourseCategoryId(), true, true, 1, 0, null, null, 0, 5);
         List<CourseBasicBo> courseBasicBos = (List) subCategoryCourses.getItems();
         if(!courseBasicBos.isEmpty()){
             portalBriefCourseBos =convertToPortalBriefCourseBoList(courseBasicBos);
         }
         return portalBriefCourseBos;
     }


      //包装课时DTO
    private List<PortalBriefCourseBo> convertToPortalBriefCourseBoList(List<CourseBasicBo> subCategoryCourses){
        List<PortalBriefCourseBo> portalBriefCourseBoList = new ArrayList<PortalBriefCourseBo>();
        for (CourseBasicBo courseBasicBo:subCategoryCourses){
            PortalBriefCourseBo portalBriefCourseBo = new PortalBriefCourseBo();
            BeanUtils.copyProperties(courseBasicBo, portalBriefCourseBo);
            if (courseBasicBo.getThumbnailId() != null) {
                FileStorageBo file = fileStorageService.getStore(courseBasicBo.getThumbnailId());
                portalBriefCourseBo.setThumbnailPath(PathUtils.getFilePath(file));
            }
            portalBriefCourseBoList.add(portalBriefCourseBo);
        }
        return portalBriefCourseBoList;
    }


}
