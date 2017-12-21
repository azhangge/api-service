package com.huajie.utils;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.util.ResourceUtils;

import com.github.tobato.fastdfs.domain.StorePath;
import com.huajie.educomponent.pubrefer.bo.FileStorageBo;
import com.huajie.educomponent.usercenter.bo.SysInfoBo;

/**
 * Created by Lenovo on 2017/8/17.
 */
public class PathUtils {

    private static final String FILE_SERVICE_HOST = "192.168.1.250";
    private static final String HTML_FILE_SERVICE_HOST = "192.168.1.250";
    private static final String TRANSFORMS_FILE_DIR = "/opt/transfiles";
    private static final String DST_PATH = "/backup";  //文件同步目标路径

	public static String getFilePath(FileStorageBo file){
        if (file == null || file.getStorageGroup() == null || file.getStoragePath() == null){
            return null;
        }
        StringBuffer filePath = new StringBuffer("http://" + FILE_SERVICE_HOST + "/" + file.getStorageGroup() + "/" + file.getStoragePath());
        return filePath.toString();
    }

    public static String getSysPath(SysInfoBo sysInfoBo){
        if (sysInfoBo == null || sysInfoBo.getSysHost() == null || sysInfoBo.getSysPort() == null){
            return null;
        }
        StringBuffer sysPath = new StringBuffer("http://" + sysInfoBo.getSysHost() + ":" + sysInfoBo.getSysPort());
        return sysPath.toString();
    }
    
    public static String getStorePath(StorePath storePath){
        if (storePath == null || storePath.getGroup() == null || storePath.getGroup() == null){
            return null;
        }
        StringBuffer filePath = new StringBuffer("http://" + FILE_SERVICE_HOST + "/" + storePath.getFullPath());
        return filePath.toString();
    }
    
    /**
     * 拼接转换文件的路径
     * @return
     */
	public static String getHtmlBaseDir() {
		return TRANSFORMS_FILE_DIR;
	}
	
	/**
	 * 获取工程类路径的目录结构
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String getClassPath() {
		File classFile = null;
		try {
			classFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return classFile.getPath();
	}
	
	/**
	 * 获取课程文件的访问路径
	 * @return
	 */
	public static String getHtmlVisitPath(FileStorageBo file) {
		if (file == null || file.getTransformFilePath() == null ){
			return null;
		}
		String transformFilePath = file.getTransformFilePath();
		StringBuffer htmlPath = new StringBuffer("http://" + HTML_FILE_SERVICE_HOST +File.separator+"doc"+File.separator+transformFilePath.replaceAll("\\\\", "/"));
        return htmlPath.toString();
	}

	public static String getDstPath() {
		return DST_PATH;
	}
}
