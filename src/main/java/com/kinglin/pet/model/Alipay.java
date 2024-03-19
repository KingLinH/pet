package com.kinglin.pet.model;

import lombok.Data;

/**
 * @author huangjl
 * @description
 * @since 2024-03-19 15:56
 */
@Data
public class Alipay {
    private String tradeNo;
    private double totalAmount;
    private String subject;
    private String alipayTraceNo;
}
