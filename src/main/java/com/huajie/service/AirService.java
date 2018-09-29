package com.huajie.service;

import com.huajie.dao.AirDao;
import com.huajie.entity.Air;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirService {

    @Autowired
    private AirDao airDao;

    public void insertAir(Air air){
        airDao.insertAir(air);
    }
}
