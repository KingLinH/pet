package com.kinglin.pet.service.impl;

import com.kinglin.pet.entity.Pet;
import com.kinglin.pet.dao.PetMapper;
import com.kinglin.pet.service.PetService;
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
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet> implements PetService {

}
