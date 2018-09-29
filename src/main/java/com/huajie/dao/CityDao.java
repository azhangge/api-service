package com.huajie.dao;

import com.huajie.entity.City;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDao {
    void insertCity(City city);
}
