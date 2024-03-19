package com.kinglin.pet.dao;

import com.kinglin.pet.entity.Appointment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 预约信息表 Mapper 接口
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
public interface AppointmentMapper extends BaseMapper<Appointment> {

    Appointment getBySn(@Param("sn") String sn);

    void updateState(@Param("sn") String sn,
                     @Param("payStateNo") Integer payStateNo,
                     @Param("gmtPayment") String gmtPayment,
                     @Param("tradeNo") String tradeNo);

    Appointment getByTradeNo(@Param("tradeNo") String tradeNo);
}
