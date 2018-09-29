package com.huajie.service;

import com.huajie.dao.CityDao;
import com.huajie.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @Autowired
    private CityDao cityDao;

    public void insertCity(City city){
        cityDao.insertCity(city);
    }
}
