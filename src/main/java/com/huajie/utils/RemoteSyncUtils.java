package com.huajie.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.*;

/**
 * Created by xuxiaolong on 2017/10/24.
 */
public class RemoteSyncUtils {
    private static Logger logger = LoggerFactory.getLogger(RemoteSyncUtils.class);

    public static void syncFile(final String srcPath,final String dstPath) {
        new Thread(){
            @Override
            public void run() {
                int rsyncStatus = 1;
                try {
                    String commandStr = createCommand(srcPath,dstPath);
                    //执行同步命令
                    rsyncStatus =  runCommand("bash","-c",commandStr);
                }  catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(rsyncStatus != 0){
                    logger.error("执行同步命令失败！");
                }
            }
        }.start();
    }

    /**
     *
     * @param srcPath   源文件路径
     * @param dstPath   目标路径
     * @return
     */
    private static String createCommand(String srcPath,String dstPath) {
        if(!StringUtils.isNotBlank(srcPath)){
            logger.error("未指定同步源文件路径");
            return "";
        }
        if(!StringUtils.isNotBlank(dstPath)){
            logger.error("未指定同步目标路径");
            return "";
        }
        StringBuffer commandStr = new StringBuffer();

        commandStr.append("rsync -avzqP ").append(srcPath).append(" ").append(dstPath);

        return commandStr.toString();
    }

    private static int runCommand(String... command) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command);
        Process p = pb.start();
        //输出进程的错误流
        InputStream errorStream = p.getErrorStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(errorStream));
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line != null){
                    logger.error(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                errorStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return p.waitFor();
    }
}