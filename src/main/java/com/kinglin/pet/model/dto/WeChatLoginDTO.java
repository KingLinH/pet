package com.kinglin.pet.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huangjl
 * @description 微信登录DTO
 * @since 2023-05-15 9:53
 */
@Data
public class WeChatLoginDTO {

    @ApiModelProperty("微信code")
    private String code;

    @ApiModelProperty("非敏感字段")
    private String rawData;

    @ApiModelProperty("签名")
    private String signature;

    @ApiModelProperty("敏感字段")
    private String encryptedData;

    @ApiModelProperty("解密向量")
    private String iv;

}
