package com.huajie.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "walter")
public class Walter {
    private String id;
    private String walterName; //水来源，比如河名 湖名
    private Float clarity;//清晰度%
    private Float mineral;//矿物质%
    private Float organism;//有机物%
    private Float microorganism;//微生物%

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWalterName() {
        return walterName;
    }

    public void setWalterName(String walterName) {
        this.walterName = walterName;
    }

    public Float getClarity() {
        return clarity;
    }

    public void setClarity(Float clarity) {
        this.clarity = clarity;
    }

    public Float getMineral() {
        return mineral;
    }

    public void setMineral(Float mineral) {
        this.mineral = mineral;
    }

    public Float getOrganism() {
        return organism;
    }

    public void setOrganism(Float organism) {
        this.organism = organism;
    }

    public Float getMicroorganism() {
        return microorganism;
    }

    public void setMicroorganism(Float microorganism) {
        this.microorganism = microorganism;
    }
}
