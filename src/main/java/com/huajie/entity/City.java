package com.huajie.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "city")
public class City {
    private String id;
    private String cityName;
    private Integer cityLevel;
    private Float gdp;
    private Float peopleNum;

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
}
