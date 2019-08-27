package com.iwei.wxmanagement.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Arrays;
import java.util.List;

public class PropertiesCopyUtil {

    /**
     * 属性复制
     *
     * @param source
     * @param clazz
     * @return
     */
    public static <T> T copyProperties(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        try {
            T target = BeanUtils.instantiate(clazz);
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 忽略部分属性
     *
     * @param source
     * @param clazz
     * @param ignoreProperties
     * @return
     */
    public static <T> T copyPropertiesIgnore(Object source, Class<T> clazz,
                                             String... ignoreProperties) {
        if (source == null) {
            return null;
        }
        try {
            T target = BeanUtils.instantiate(clazz);
            BeanUtils.copyProperties(source, target, ignoreProperties);
            return target;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 复制部分属性
     *
     * @param source
     * @param clazz
     * @param specificProperties
     * @return
     */
    public static <T> T copyPropertiesSpecific(Object source, Class<T> clazz,
                                               String... specificProperties) {
        if (source == null) {
            return null;
        }
        try {
            T target = BeanUtils.instantiate(clazz);
            if (specificProperties == null) {
                return target;
            }
            List<String> specificList = Arrays.asList(specificProperties);
            copySpecificProperties(source, target, specificList);
            return target;
        } catch (Exception e) {
        }
        return null;
    }


    private static void copySpecificProperties(final Object source,
                                               final Object target, final Iterable<String> properties) {


        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper trg = new BeanWrapperImpl(target);


        for (final String propertyName : properties) {
            trg.setPropertyValue(propertyName,
                    src.getPropertyValue(propertyName));
        }


    }

}
