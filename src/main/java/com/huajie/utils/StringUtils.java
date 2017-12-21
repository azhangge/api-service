package com.huajie.utils;

/**
 * Created by 10070 on 2017/8/12.
 */
public class StringUtils {

    public static boolean isNotBlank(String source){
        if (source != null) {
            source = source.replace(" ", "");
        }
        return source != null && source.length() > 0;
    }

}
