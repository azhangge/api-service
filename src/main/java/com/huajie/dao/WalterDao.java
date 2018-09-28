package com.huajie.dao;

import com.huajie.entity.Walter;
import com.huajie.entity.vo.WalterVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WalterDao {
    //查询
    //一个参数，参数明dao与mapper可以不一样
    List<WalterVo> findWalter(String n_ame4);

    //2个参数时，3个方法：1 用@param指定  2 对象  3 用mybatis封装的map的key(arg0,param1)
    List<WalterVo> findWalter2Param(String id, String name);

    //插入
    //对象一定要用param指定
    void insertWalter(@Param("walter") Walter walter);

    //批量插入
    void insertWalters(@Param("walters") List<Walter> walters);
}
