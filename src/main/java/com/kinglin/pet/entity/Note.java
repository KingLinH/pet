package com.kinglin.pet.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 事件记录表
 * </p>
 *
 * @author huangjl
 * @since 2024-10-15
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("note")
@ApiModel(value = "Note对象", description = "事件记录表")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("宠物id")
    @TableField("pet_id")
    private Long petId;

    @ApiModelProperty("记录类型")
    @TableField("type")
    private String type;

    @ApiModelProperty("发生时间")
    @TableField("happen_time")
    private LocalDateTime happenTime;

    @ApiModelProperty("备注")
    @TableField("content")
    private String content;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
