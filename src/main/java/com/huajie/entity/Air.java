package com.huajie.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "air")
public class Air {
    private String id;
    private String cityId;
    private Integer pm;
    private Integer see;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Integer getPm() {
        return pm;
    }

    public void setPm(Integer pm) {
        this.pm = pm;
    }

    public Integer getSee() {
        return see;
    }

    public void setSee(Integer see) {
        this.see = see;
    }
}
