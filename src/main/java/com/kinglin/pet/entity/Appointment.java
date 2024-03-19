package com.kinglin.pet.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("单号")
    @TableField("sn")
    private String sn;

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

    @ApiModelProperty("支付状态")
    @TableField("pay_state_no")
    private Byte payStateNo;

    @ApiModelProperty("支付宝交易凭证")
    @TableField("trade_no")
    private String tradeNo;

    @ApiModelProperty("付款时间")
    @TableField("gmt_payment")
    private String gmtPayment;

}
