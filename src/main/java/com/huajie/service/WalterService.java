package com.huajie.service;

import com.huajie.dao.WalterDao;
import com.huajie.entity.vo.WalterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalterService {

    //walterDao在mapper里面反向代理，这里不给spring管理bean也可以
    @Autowired
    private WalterDao walterDao;

    public List<WalterVo> findByName(String name){
        return walterDao.findWalter(name);
    }
}
