package com.huajie.controller;

import com.huajie.entity.Air;
import com.huajie.service.AirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AirController {

    @Autowired
    private AirService airService;

    @RequestMapping(value = "/sir",method = RequestMethod.POST)
    public void insert(@RequestBody Air air){
        airService.insertAir(air);
    }
}
