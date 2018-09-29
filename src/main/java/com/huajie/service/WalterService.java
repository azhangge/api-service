package com.huajie.service;

import com.huajie.dao.WalterDao;
import com.huajie.entity.Walter;
import com.huajie.entity.vo.WalterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalterService {

    //walterDao在mapper里面反向代理，这里不给spring管理bean也可以
    @Autowired
    private WalterDao walterDao;

//    @Cacheable(value = "aaa",key = "#name")
    public List<WalterVo> findByName(String name){
        return walterDao.findWalter(name);
    }

    public List<WalterVo> findWalter2Param(String id, String name){
        return walterDao.findWalter2Param(id, name);
    }

    public void insertWalter(Walter walter){
        walterDao.insertWalter(walter);
    }

    public void insertWalters(List<Walter> walters){
        walterDao.insertWalters(walters);
    }
}
