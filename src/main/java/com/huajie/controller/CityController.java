package com.huajie.controller;

import com.huajie.entity.City;
import com.huajie.service.CityService;
import com.huajie.vo.CityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/city",method = RequestMethod.POST)
    public void insert(@RequestBody City city){
        cityService.insertCity(city);
    }

    @RequestMapping(value = "/city",method = RequestMethod.GET)
    public List<CityVo> findCityDetail(@RequestParam(value = "cityId") String cityId){
        int n = 10/0;
        return cityService.findCityDetail(cityId);
    }

}
