package com.huajie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author Administrator
 * @Date 2018/12/28 13:58
 * @Description
 */

@Controller
@RequestMapping(value = "/fs")
public class UploadFileController {


    @RequestMapping(value = "/upload" ,method = RequestMethod.POST)
    public void upload(MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) throws IOException {

//        if (new File("C:/Users/Administrator/Desktop/filetest/a.txt").exists()){
//            new File("C:/Users/Administrator/Desktop/filetest/a.txt").delete();
//        }

        if (new File("C:\\Users\\Administrator\\Desktop\\filetest\\a.txt").exists()){
            new File("C:\\Users\\Administrator\\Desktop\\filetest\\a.txt").delete();
        }

        File file = new File("C:\\Users\\Administrator\\Desktop\\filetest\\a.txt");

        OutputStream out = new FileOutputStream(file);

        for (MultipartFile f:files){
            InputStream is = f.getInputStream();

            int len = 512;
            byte[] sb = new byte[len];
            while ((len = is.read(sb)) !=-1) {
                out.write(sb,0, len);
            }
        }


        out.flush();
        out.close();

    }
}
