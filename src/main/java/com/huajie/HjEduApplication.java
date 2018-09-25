package com.huajie;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;


/**
 * Created by fangxing on 17-7-3.
 */

@MapperScan("com.huajie.dao")
@SpringBootApplication
@Controller
public class HjEduApplication {

    @Autowired
    private Environment env;

    private  final Logger logger = LoggerFactory.getLogger(HjEduApplication.class);

    public static void main(String[] args){
        SpringApplication.run(HjEduApplication.class, args);
    }
}
