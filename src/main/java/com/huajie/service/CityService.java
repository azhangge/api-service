package com.huajie.service;

import com.huajie.dao.CityDao;
import com.huajie.entity.City;
import com.huajie.entity.Water;
import com.huajie.vo.CityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityDao cityDao;

    public void insertCity(City city){
        cityDao.insertCity(city);
    }

    public List<CityVo> findCityDetail(String cityId){
        return cityDao.getCityDetail(cityId);
    }

    public List<Water> findWaterByCityId(String cityId){
        return cityDao.getWaterByCityId(cityId);
    }
}
