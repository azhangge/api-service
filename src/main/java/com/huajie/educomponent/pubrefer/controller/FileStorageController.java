package com.huajie.educomponent.pubrefer.controller;


import com.huajie.appbase.BaseRetBo;
import com.huajie.appbase.BaseRetMessage;
import com.huajie.appbase.BaseRetType;
import com.huajie.appbase.BusinessException;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.pubrefer.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Created by fangxing on 17-7-14.
 */
@RestController
@RequestMapping("/fs")
public class FileStorageController {
    @Autowired
    private FileStorageService fileStorageService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public BaseRetBo upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        BaseRetBo retBo = new BaseRetBo();
        try {
            FileStorageBo result = fileStorageService.uploadFile(file.getInputStream(), file.getOriginalFilename(), file.getSize());
            return retBo.setBaseRetBo(BaseRetType.SUCCESS.getValue(), BaseRetMessage.SUCCESS.getValue(), result);
        } catch (BusinessException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
        } catch (IOException e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), BaseRetMessage.SERVICE_ERROR.getValue());
        } catch (Exception e) {
            return retBo.setBaseRetBo(BaseRetType.FAILED.getValue(), e.getMessage());
		}
    }
    

    @RequestMapping("/delete/{fileid}")
    @ResponseBody
    public BaseRetBo deleteIdleFiles(@PathVariable String fileid) {
        BaseRetBo retBo = new BaseRetBo();
        fileStorageService.deleteFile(fileid);
        return retBo;
    }

    @RequestMapping("/delidle")
    @ResponseBody
    public BaseRetBo deleteIdleFiles() {
        BaseRetBo retBo = new BaseRetBo();
        fileStorageService.deleteIdleFile();
        return retBo;
    }

}