package com.huajie.utils;

import org.apache.commons.beanutils.PropertyUtilsBean;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangd on 2017/9/19.
 */
public class PropertiesUtils {

    public static <T> List<Object> getPropertyList(List<T> objList, String propertyName) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        if (objList == null || objList.size() == 0)
            throw new IllegalArgumentException("No objList specified");
        if (propertyName == null || "".equals(propertyName)) {
            throw new IllegalArgumentException("No propertyName specified for bean class '" + objList.get(0).getClass() + "'");
        }
        PropertyUtilsBean p = new PropertyUtilsBean();
        List<Object> propList = new LinkedList<Object>();
        for (int i = 0; i < objList.size(); i++) {
            T obj = objList.get(i);
            propList.add(p.getProperty(obj, propertyName));
        }
        return propList;
    }


}
