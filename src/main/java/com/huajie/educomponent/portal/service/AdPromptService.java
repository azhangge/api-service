package com.huajie.educomponent.portal.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huajie.appbase.BusinessException;
import com.huajie.appbase.PageResult;
import com.huajie.educomponent.course.dao.CourseBasicJpaRepo;
import com.huajie.educomponent.course.entity.CourseBasicEntity;
import com.huajie.educomponent.exam.dao.ExamNoticeJpaRepo;
import com.huajie.educomponent.exam.entity.ExamNoticeEntity;
import com.huajie.educomponent.portal.bo.AdPromptBo;
import com.huajie.educomponent.portal.constants.RedisKeys;
import com.huajie.educomponent.portal.constants.ResourceType;
import com.huajie.educomponent.portal.dao.AdPromptJpaRepo;
import com.huajie.educomponent.portal.entity.AdPromptEntity;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.service.FileStorageService;
import com.huajie.utils.PathUtils;
import com.huajie.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zgz on 2017/8/24.
 */
@Service
public class AdPromptService {

    @Autowired
    private AdPromptJpaRepo adPromptJpaRepo;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CourseBasicJpaRepo courseBasicJpaRepo;

    @Autowired
    private ExamNoticeJpaRepo examNoticeJpaRepo;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 保存、更新轮播
     * @param adPromptBo
     * @return
     */
    public AdPromptBo save(@RequestBody AdPromptBo adPromptBo){

        AdPromptEntity adPromptEntity = new AdPromptEntity();
        if (StringUtils.isNotBlank(adPromptBo.getAdPromptId())) {
            adPromptEntity = adPromptJpaRepo.findOne(adPromptBo.getAdPromptId());
        }
        BeanUtils.copyProperties(adPromptBo, adPromptEntity);
        AdPromptEntity result = adPromptJpaRepo.save(adPromptEntity);
        return convertToBo(result);
    }

    /**
     * 查询轮播
     * @param isValid
     * @return
     */
    public List<AdPromptBo> get(Boolean isValid){
        List<AdPromptBo> result = null;

        if (isValid != null && isValid.booleanValue() == true) {
            result = getAdPromptFromRedis();
        }

        if (result != null && result.size() > 0) {
            return result;
        }

        List<AdPromptEntity> entities;
        if (isValid == null) {
            entities = adPromptJpaRepo.list();
        }else {
            entities = adPromptJpaRepo.listByValide(isValid);
        }
        for (AdPromptEntity entity:entities){
            result.add(convertToBo(entity));
        }

        if (isValid != null && isValid.booleanValue() == true) {
            saveToRedis(result);
        }

        return result;
    }

    private List<AdPromptBo> getAdPromptFromRedis() {
        return redisTemplate.execute(new RedisCallback<List<AdPromptBo>>() {
            public List<AdPromptBo> doInRedis(RedisConnection connection) {
                List<AdPromptBo> result = new ArrayList<>();
                byte[] key = redisTemplate.getStringSerializer().serialize(RedisKeys.HOME_SCROLL_BAR);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);

                    RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                    String jsonValue = serializer.deserialize(value);

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<AdPromptBo>>() {
                    }.getType();
                    result = gson.fromJson(jsonValue, type);

                }

                return result;
            }
        });
    }

    private void saveToRedis(final List<AdPromptBo> adPromptBos) {
        final Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) {

                byte[] keyByte = redisTemplate.getStringSerializer().serialize(RedisKeys.HOME_SCROLL_BAR);

                Gson gson = new Gson();
                Type type = new TypeToken<List<AdPromptBo>>() {
                }.getType();
                String valueString = gson.toJson(adPromptBos, type);
                byte[] valueByte = redisTemplate.getStringSerializer().serialize(valueString);
                connection.set(keyByte, valueByte);

                return true;
            }
        });
    }


    /**
     *删除轮播
     * @param adPromptId
     * @return
     */
    public void delete(String adPromptId) throws Exception {
        AdPromptEntity entity = adPromptJpaRepo.findOne(adPromptId);
        if (entity == null){
            throw new Exception("轮播图资源不存在");
        }
        entity.setDeleted(true);
        adPromptJpaRepo.save(entity);
    }

    public PageResult<AdPromptBo> getAdPrompts(Integer resourceType, String search,int pageIndex, int pageSize) {
        PageResult<AdPromptBo> adPromptBoPageResult = new PageResult<AdPromptBo>();
        Map<String, Object> param = new HashMap<String, Object>();
        if(StringUtils.isNotBlank(search)){
            param.put("search",search);
        }
        if(resourceType != null){
            param.put("resourceType",resourceType);
        }
        PageResult<AdPromptEntity> adPromptEntityPageResult = adPromptJpaRepo.findByCondition(param, pageIndex, pageSize);
        List<AdPromptEntity> adPromptEntities = (List<AdPromptEntity>) adPromptEntityPageResult.getItems();
        if(!adPromptEntities.isEmpty()) {
            List<AdPromptBo> adPromptBos = new ArrayList<AdPromptBo>();
            for (AdPromptEntity adPromptEntity : adPromptEntities) {
                AdPromptBo adPromptBo =new AdPromptBo();
                BeanUtils.copyProperties(adPromptEntity,adPromptBo);
                adPromptBo.setAdPromptId(adPromptEntity.getId());
                adPromptBos.add(adPromptBo);
            }
            adPromptBoPageResult.setItems(adPromptBos);
            adPromptBoPageResult.setTotal(adPromptEntityPageResult.getTotal());
        }
        return adPromptBoPageResult;
    }

    private AdPromptBo convertToBo(AdPromptEntity entity){
        if (entity != null){
            AdPromptBo bo = new AdPromptBo();
            BeanUtils.copyProperties(entity, bo);
            if (entity.getMobilAdImgId() != null){
                FileStorageBo file = fileStorageService.getStore(entity.getMobilAdImgId());
                bo.setMobilAdImgPath(PathUtils.getFilePath(file));
            }
            if (entity.getWebAdImgId() != null){
                FileStorageBo file = fileStorageService.getStore(entity.getWebAdImgId());
                bo.setWebAdImgPath(PathUtils.getFilePath(file));
            }
            bo.setAdPromptId(entity.getId());
            //// TODO: 2017/8/24 mock数据
            bo.setResourceUrl("#");
            return bo;
        }
        return null;
    }

    public AdPromptBo getOne(String adPromptId) {
        AdPromptEntity adPromptEntity = adPromptJpaRepo.getOne(adPromptId);
        if(adPromptEntity == null) {
            throw new BusinessException("轮播图不存在");
        }
        AdPromptBo adPromptBo = new AdPromptBo();
        //BeanUtils.copyProperties(adPromptEntity,adPromptBo);
        adPromptBo = convertToBo(adPromptEntity);
        if(adPromptEntity.getResourceType() == ResourceType.COURSE.getValue()) {
            CourseBasicEntity courseBasicEntity = courseBasicJpaRepo.findById(adPromptEntity.getResourceId());
            if(courseBasicEntity != null) {
                adPromptBo.setResourceName(courseBasicEntity.getCourseName());
            }
        } else if(adPromptEntity.getResourceType() == ResourceType.NOTICE.getValue()) {
            ExamNoticeEntity examNoticeEntity = examNoticeJpaRepo.getOne(adPromptEntity.getResourceId());
            if(examNoticeEntity != null) {
                adPromptBo.setResourceName(examNoticeEntity.getNoticeName());
            }
        }

        adPromptBo.setAdPromptId(adPromptEntity.getId());
        return adPromptBo;
    }
}
