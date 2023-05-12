package com.kinglin.pet.service.impl;

import com.kinglin.pet.entity.Appointment;
import com.kinglin.pet.dao.AppointmentMapper;
import com.kinglin.pet.service.AppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 预约信息表 服务实现类
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

}
