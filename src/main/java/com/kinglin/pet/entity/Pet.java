package com.kinglin.pet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 宠物信息表
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@Data
@TableName("pet")
@ApiModel(value = "Pet对象", description = "宠物信息表")
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("宠物名")
    @TableField("name")
    private String name;

    @ApiModelProperty("宠物物种")
    @TableField("species")
    private String species;

    @ApiModelProperty("宠物品种")
    @TableField("breed")
    private String breed;

    @ApiModelProperty("宠物性别")
    @TableField("gender")
    private String gender;

    @ApiModelProperty("宠物生日")
    @TableField("birth_date")
    private LocalDate birthDate;

    @ApiModelProperty("宠物主人")
    @TableField("owner_id")
    private Long ownerId;


}
