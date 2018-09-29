package com.huajie.vo;

import com.huajie.entity.Air;
import com.huajie.entity.Water;

import java.util.List;

public class CityVo {
    private String id;
    private String cityName;
    private Integer cityLevel;
    private Float gdp;
    private Float peopleNum;
    private List<Air> airs;
    private List<Water> waters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(Integer cityLevel) {
        this.cityLevel = cityLevel;
    }

    public Float getGdp() {
        return gdp;
    }

    public void setGdp(Float gdp) {
        this.gdp = gdp;
    }

    public Float getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Float peopleNum) {
        this.peopleNum = peopleNum;
    }

    public List<Air> getAirs() {
        return airs;
    }

    public void setAirs(List<Air> airs) {
        this.airs = airs;
    }

    public List<Water> getWaters() {
        return waters;
    }

    public void setWaters(List<Water> waters) {
        this.waters = waters;
    }
}
