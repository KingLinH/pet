package com.kinglin.pet.test.file;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ApportionmentEntity {
    private Long id;
    private Long memberId;
    private String memberName;
    private Long taskDetailId;
    private Long extractId;
    private String objtctSn;
    private String objtctName;
    private Long objectId;
    private Integer type;
    private Integer sourceType;
    private Long parentId;
    private Long standardId;
    private String standardName;
    private BigDecimal standardValue;
    private BigDecimal amount;
    private String accountMonth;
    private Byte isDeleted;
    private String content;
    private Date gmtCreate;
    private Date gmtModified;
}