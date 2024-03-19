package com.kinglin.pet.test.other;

import java.math.BigDecimal;

/**
 * @author huangjl
 * @description
 * @since 2023-09-08 11:20
 */
public class MainTest {

    public static void main(String[] args) {
        BigDecimal test = BigDecimal.ZERO;
        try {
            test = BigDecimal.ONE.divide(new BigDecimal("3"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(test);
    }

}
