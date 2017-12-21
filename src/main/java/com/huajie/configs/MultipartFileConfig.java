package com.huajie.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Created by fangxing on 17-7-14.
 */
@Configuration
public class MultipartFileConfig {
    @Value("${multipart_file_size}")
    private String fileSize;
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize(fileSize); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(fileSize);
        return factory.createMultipartConfig();
    }


}
