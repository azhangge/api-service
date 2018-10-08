package com.huajie.dao;

import com.huajie.entity.Water;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface WaterDao {
    //查询
    //一个参数，参数明dao与mapper可以不一样
    //用了<if test>之类的标签，不许用param指定参数
    List<Water> findWater(@Param("name") String name);

    //2个参数时，3个方法：1 用@param指定  2 对象  3 用mybatis封装的map的key(arg0,param1)
    List<Water> findWater2Param(String id, String name);

    //插入
    //对象一定要用param指定
    void insertWater(@Param("Water") Water water);

    //批量插入
    void insertWaters(@Param("Waters") List<Water> waters);

    List<Water> findByIds(@Param("ids") String[] ids);
    List<Water> findByMapIds(@Param("ids") Map<String,String> ids);
}
