package com.huajie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by fangxing on 17-7-3.
 */

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
