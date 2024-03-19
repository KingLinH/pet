package com.kinglin.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kinglin.pet.entity.Service;
import com.kinglin.pet.dao.ServiceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 服务信息表 服务实现类
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@org.springframework.stereotype.Service
public class ServiceServiceImpl extends ServiceImpl<ServiceMapper, Service> implements IService<Service> {

}
