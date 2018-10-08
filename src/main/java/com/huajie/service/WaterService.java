package com.huajie.service;

import com.huajie.dao.WaterDao;
import com.huajie.entity.Water;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaterService {

    //WaterDao在mapper里面反向代理，这里不给spring管理bean也可以
    @Autowired
    private WaterDao waterDao;

//    @Cacheable(value = "aaa",key = "#name")
    public List<Water> findByName(String name){
        return waterDao.findWater(name);
    }

    public List<Water> findWater2Param(String id, String name){
        return waterDao.findWater2Param(id, name);
    }

    public void insertWater(Water water){
        waterDao.insertWater(water);
    }

    public void insertWaters(List<Water> waters){
        waterDao.insertWaters(waters);
    }

    public List<Water> findByIds(String[] ids){
        return waterDao.findByIds(ids);
    }
}
