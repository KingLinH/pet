package com.kinglin.pet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 门店信息表
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@Data
@TableName("store")
@ApiModel(value = "Store对象", description = "门店信息表")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("门店名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("门店地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("门店联系方式")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("门店邮箱")
    @TableField("email")
    private String email;


}
