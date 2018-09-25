package com.huajie.service;

import com.huajie.dao.WalterDao;
import com.huajie.vo.WalterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalterService {

    @Autowired
    private WalterDao walterDao;

    public List<WalterVo> findByName(String name){
        return walterDao.findByName(name);
    }
}
