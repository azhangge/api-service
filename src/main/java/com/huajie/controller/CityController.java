package com.huajie.controller;

import com.huajie.entity.City;
import com.huajie.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/city",method = RequestMethod.POST)
    public void insert(City city){
        cityService.insertCity(city);
    }
}
