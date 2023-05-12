package com.kinglin.pet.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 预约信息表
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@Data
@TableName("appointment")
@ApiModel(value = "Appointment对象", description = "预约信息表")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("开始时间")
    @TableField("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty("宠物id")
    @TableField("pet_id")
    private Long petId;

    @ApiModelProperty("服务id")
    @TableField("service_id")
    private Long serviceId;

    @ApiModelProperty("门店id")
    @TableField("store_id")
    private Long storeId;

    @ApiModelProperty("预约状态")
    @TableField("status")
    private String status;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("预约时间")
    @TableField("appointment_time")
    private LocalDateTime appointmentTime;


}
