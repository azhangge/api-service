package com.huajie.dao;

import com.huajie.entity.vo.WalterVo;
import java.util.List;

public interface WalterDao {
    List<WalterVo> findByName(String name);
}
