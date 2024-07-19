package com.kinglin.pet.util;

import cn.hutool.core.util.ArrayUtil;
import com.kinglin.pet.exception.PetException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
public class Util {

    /**
     * 平均分配
     */
    public static <V> void listAverage(List<V> entityList, String weightKey, String targetKey) {

        if (ArrayUtil.isEmpty(entityList)) {
            return;
        }
        // 获取字段对应的映射
        Field weightField = ReflectionUtil.getFieldByName(entityList.get(0).getClass(), weightKey);
        Field targetField = ReflectionUtil.getFieldByName(entityList.get(0).getClass(), targetKey);
        if (Objects.isNull(weightField)) {
            throw new PetException("listAverage - 反射字段" + weightKey + "不存在");
        }
        if (Objects.isNull(targetField)) {
            throw new PetException("listAverage - 反射字段" + targetKey + "不存在");
        }
        weightField.setAccessible(Boolean.TRUE);
        targetField.setAccessible(Boolean.TRUE);

        BigDecimal weight;
        try {
            weight = entityList.stream().filter(e -> {
                try {
                    return Objects.nonNull(e) && Objects.nonNull(weightField.get(e));
                } catch (IllegalAccessException ex) {
                    log.warn("listAverage, ex", ex);
                    return false;
                }
            }).map(e -> {
                BigDecimal keyV = BigDecimal.ZERO;
                try {
                    keyV = (BigDecimal) weightField.get(e);
                } catch (IllegalAccessException ex) {
                    log.warn("listAverage, ex", ex);
                }
                return keyV;
            }).reduce(BigDecimal.ZERO, BigDecimal::add);

            if (weight.compareTo(BigDecimal.ZERO) <= 0) {
                entityList.forEach(e -> {
                    try {
                        targetField.set(e, BigDecimal.ZERO);
                    } catch (IllegalAccessException ex) {
                        log.warn("listAverage, ex", ex);
                    }
                });
            } else {
                BigDecimal shared = BigDecimal.ZERO;
                for (int i = 0; i < entityList.size(); i++) {
                    V v = entityList.get(i);
                    if (i == entityList.size() - 1) {
                        // targetField.set(v, Util.safeSubtract(false, weight, shared));
                    } else {
                        // shareAmount = Util.safeDivide(Util.safeMultiply(productCostingResultDTO.getQtyStock(), weight), taskDetailQtyStockTotal, BigDecimal.ZERO);
                        // shared = Util.safeAdd(shared, shareAmount);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("listAverage, ex", e);
        } finally {
            weightField.setAccessible(Boolean.FALSE);
            targetField.setAccessible(Boolean.FALSE);
        }

    }

}
