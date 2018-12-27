package com.huajie.controller;

import com.huajie.entity.Water;
import com.huajie.service.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class WaterController {
    @Autowired
    private WaterService waterService;

    @RequestMapping(value = "/water",method = RequestMethod.GET)
    public List<Water> findByName(@RequestParam(value = "name") String name){
        throw new RuntimeException("aa");
//        return waterService.findByName(name);
    }

    @RequestMapping(value = "/water/param2",method = RequestMethod.GET)
    public List<Water> findWater2Param(@RequestParam(value = "id") String id,
                                        @RequestParam(value = "name") String name){
        return waterService.findWater2Param(id,name);
    }

    @RequestMapping(value = "/water/insert",method = RequestMethod.POST)
    public void findWater2Param(@RequestBody Water water){
        waterService.insertWater(water);
    }

    @RequestMapping(value = "/water/inserts",method = RequestMethod.POST)
    public void findWater2Param(@RequestBody List<Water> waters){
        waterService.insertWaters(waters);
    }

    @RequestMapping(value = "/water/find",method = RequestMethod.GET)
    public List<Water> findByIds(@RequestParam(value = "ids") String[] ids){
        return waterService.findByIds(ids);
    }

    @RequestMapping(value = "/water/findByMapIds",method = RequestMethod.GET)
    public List<Water> findByMapIds(){
        Map<String,String> ids = new HashMap<>();
        ids.put("11","2");
        ids.put("22","1");
        return waterService.findByMapIds(ids);
    }
}
