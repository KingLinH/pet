package com.kinglin.pet.test.file;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductCostingEntity {
    private Long id;
    private Long memberId;
    private String memberName;
    private Integer no;
    private Long orderId;
    private String orderBillSn;
    private Long putId;
    private String putBillSn;
    private Long putDetailId;
    private Long wareHouseId;
    private String wareHouseName;
    private Long taskDetailId;
    private String taskDetailSn;
    private Long productSkuId;
    private BigDecimal qtyPut;
    private Date accountMonth;
    private BigDecimal materialCost;
    private BigDecimal personCost;
    private BigDecimal manufactureCost;
    private BigDecimal otherCost;
    private BigDecimal totalCost;
    private BigDecimal costPrice;
    private BigDecimal unitPriceCost;
    private String prompt;
    private Byte isDeleted;
    private String remark;
    private Date gmtCreate;
    private Date gmtModified;
}