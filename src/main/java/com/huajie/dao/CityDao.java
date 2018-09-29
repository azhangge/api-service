package com.huajie.dao;

import com.huajie.entity.Air;
import com.huajie.entity.City;
import com.huajie.entity.Water;
import com.huajie.vo.CityVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityDao {
    void insertCity(City city);

    List<CityVo> getCityDetail(@Param("cityId") String cityId);

    List<Air> getAirByCityId(String cityId);

    List<Water> getWaterByCityId(String cityId);
}
