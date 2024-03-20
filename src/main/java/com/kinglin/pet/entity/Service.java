package com.kinglin.pet.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 服务信息表
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@Data
@TableName("service")
@ApiModel(value = "Service对象", description = "服务信息表")
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("服务名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("门店id")
    @TableField("store_id")
    private Long storeId;


}
