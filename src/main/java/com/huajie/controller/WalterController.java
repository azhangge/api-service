package com.huajie.controller;

import com.huajie.service.WalterService;
import com.huajie.entity.vo.WalterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
public class WalterController {
    @Autowired
    private WalterService walterService;

    @RequestMapping(value = "/walter",method = RequestMethod.GET)
    public List<WalterVo> findByName(@RequestParam(value = "name") String name){
        return walterService.findByName(name);
    }

    @RequestMapping(value = "/walter/param2",method = RequestMethod.GET)
    public List<WalterVo> findWalter2Param(@RequestParam(value = "id") String id,
                                           @RequestParam(value = "name") String name){
        return walterService.findWalter2Param(id,name);
    }
}
