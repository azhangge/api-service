package com.huajie.educomponent.suggestion.service;

import com.huajie.appbase.PageResult;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.service.FileStorageService;
import com.huajie.educomponent.suggestion.bo.SuggestionBo;
import com.huajie.educomponent.suggestion.dao.SuggestionJpaRepo;
import com.huajie.educomponent.suggestion.entity.SuggestionEntity;
import com.huajie.utils.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by zgz on 2017/9/6.
 */
@Service
public class SuggestionService {

    @Autowired
    private SuggestionJpaRepo suggestionJpaRepo;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 新建
     * @param suggestionBo
     * @return
     */
    public String create(String userId, SuggestionBo suggestionBo){
        String picIds = null;
        if (suggestionBo.getPicIds()!=null && suggestionBo.getPicIds().size() > 0){
            String[] picArr = new String[suggestionBo.getPicIds().size()];
            suggestionBo.getPicIds().toArray(picArr);
            if (picArr.length == 1){
                picIds = picArr[0];
            }else {
                for (int i=1;i<picArr.length;i++){
                    picIds = picArr[0]+"_"+picArr[i];
                }
            }
        }
        SuggestionEntity entity = new SuggestionEntity();
        BeanUtils.copyProperties(suggestionBo, entity);
        entity.setPicIds(picIds);
        entity.setUserId(userId);
        entity.setCreateDate(new Date());
        entity = suggestionJpaRepo.save(entity);
        return entity.getId();
    }

    /**
     * 我的意见，后续根据用户类型兼容管理员查询
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageResult<SuggestionBo> get(String userId, String keyword, Integer type, int pageIndex, int pageSize){

        PageResult<SuggestionEntity> suggestionResult = suggestionJpaRepo.listPages(userId, keyword, type, pageIndex, pageSize);
        List<SuggestionBo> suggestionBos = new ArrayList<SuggestionBo>();
        for (SuggestionEntity entity:suggestionResult.getItems()){
            SuggestionBo suggestionBo = new SuggestionBo();
            BeanUtils.copyProperties(entity, suggestionBo);
            if (entity.getPicIds() != null) {
                String[] picIds = entity.getPicIds().split("_");
                List<String> picPath = new ArrayList<String>();
                if (picIds.length > 0) {
                    for (String picId : picIds) {
                        FileStorageBo file = fileStorageService.getStore(picId);
                        picPath.add(PathUtils.getFilePath(file));
                    }
                }
                suggestionBo.setPicIds(Arrays.asList(picIds));
                suggestionBo.setPicPath(picPath);
            }
            suggestionBos.add(suggestionBo);
        }
        PageResult<SuggestionBo> result = new PageResult<SuggestionBo>();
        result.setTotal(suggestionResult.getTotal());
        result.setItems(suggestionBos);
        return result;
    }
}
