package com.huajie.dao;

import com.huajie.entity.vo.WalterVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalterDao {
    List<WalterVo> findByName(String name);
}
