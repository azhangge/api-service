package com.huajie.dao;

import com.huajie.entity.Air;
import org.springframework.stereotype.Repository;

@Repository
public interface AirDao {
    void insertAir(Air air);
}
