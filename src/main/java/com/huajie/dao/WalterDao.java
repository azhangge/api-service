package com.huajie.dao;

import com.huajie.entity.vo.WalterVo;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WalterDao {
    //一个参数，参数明dao与mapper可以不一样
    List<WalterVo> findWalter(String n_ame4);

    //2个参数时，3个方法：1 用@param指定  2 对象  3 用mybatis封装的map的key
    List<WalterVo> findWalter2Param(String id, String name);
}
