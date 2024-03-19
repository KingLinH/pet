package com.kinglin.pet.test.file;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MaterialCostExtractEntity {
    private Long id;
    private Long memberId;
    private String memberName;
    private Long orderId;
    private String orderBillSn;
    private Long taskDetailId;
    private String taskDetailSn;
    private String productSn;
    private String productName;
    private Long productSkuId;
    private String productSkuName;
    private Long costTermId;
    private String costElement;
    private String accountDocument;
    private String subjectCode;
    private String accountSubject;
    private Long companyId;
    private String companyName;
    private Long departId;
    private String departName;
    private BigDecimal amount;
    private String accountMonth;
    private Integer apportionmentState;
    private String apportionmentRemark;
    private Byte isDeleted;
    private String content;
    private Date gmtCreate;
    private Date gmtModified;
}