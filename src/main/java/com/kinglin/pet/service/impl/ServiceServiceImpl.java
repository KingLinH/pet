package com.kinglin.pet.service.impl;

import com.kinglin.pet.entity.AppointmentService;
import com.kinglin.pet.dao.ServiceMapper;
import com.kinglin.pet.service.ServiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务信息表 服务实现类
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@Service
public class ServiceServiceImpl extends ServiceImpl<ServiceMapper, AppointmentService> implements ServiceService {

}
