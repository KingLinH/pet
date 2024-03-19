package com.kinglin.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kinglin.pet.entity.Pet;
import com.kinglin.pet.dao.PetMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 宠物信息表 服务实现类
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@Service
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements IService<Pet> {

}
