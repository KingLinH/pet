package com.kinglin.pet.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 反射工具类
 */
@Slf4j
public class ReflectionUtil {

    private ReflectionUtil() {
        throw new IllegalStateException("ReflectionUtil class");
    }

    public static <T> Field getFieldByName(Class<T> clazz, String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        // 获取继承链路上所有字段
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = clazz;
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }
        Map<String, Field> fieldMap = fieldList.stream().collect(Collectors.toMap(Field::getName, e -> e, (k1, k2) -> k1));
        return fieldMap.get(fieldName);
    }

}
