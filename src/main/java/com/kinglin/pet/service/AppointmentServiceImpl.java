package com.kinglin.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kinglin.pet.dao.AppointmentMapper;
import com.kinglin.pet.entity.Appointment;
import com.kinglin.pet.enums.AppointmentStateEnum;
import com.kinglin.pet.enums.PayStateEnum;
import com.kinglin.pet.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 预约信息表 服务实现类
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements IService<Appointment> {
    @Autowired
    private AppointmentMapper appointmentMapper;

    /**
     * 根据单号查询预约信息
     *
     * @param sn 单号
     * @return 预约信息
     */
    public Appointment getBySn(String sn) {
        if (StringUtils.isBlank(sn)) {
            return null;
        }
        return appointmentMapper.getBySn(sn);
    }

    /**
     * 付款成功更新预约信息
     *
     * @param sn           预约信息编号
     * @param payStateEnum 支付状态
     * @param gmtPayment   付款时间
     * @param tradeNo      支付宝交易凭证
     */
    public void updateState(String sn, PayStateEnum payStateEnum, String gmtPayment, String tradeNo) {
        Appointment appointment = this.getBySn(sn);
        if (Objects.isNull(appointment)) {
            return;
        }
        Appointment updateEntity = new Appointment();
        updateEntity.setId(appointment.getId());
        updateEntity.setPayStateNo(payStateEnum.getCode());
        updateEntity.setGmtPayment(gmtPayment);
        updateEntity.setTradeNo(tradeNo);
        appointmentMapper.updateById(updateEntity);
    }

    /**
     * 退款时更新预约信息
     *
     * @param tradeNo      支付宝交易凭证
     * @param payStateEnum 支付状态
     */
    public void updatePayState(String tradeNo, PayStateEnum payStateEnum) {
        Appointment appointment = appointmentMapper.getByTradeNo(tradeNo);
        if (Objects.isNull(appointment)) {
            return;
        }
        Appointment updateEntity = new Appointment();
        updateEntity.setId(appointment.getId());
        updateEntity.setPayStateNo(payStateEnum.getCode());
        updateEntity.setStatus(AppointmentStateEnum.CANCELLED.getValue());
        appointmentMapper.updateById(updateEntity);
    }
}
