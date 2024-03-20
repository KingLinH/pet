package com.kinglin.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kinglin.pet.entity.Owner;
import com.kinglin.pet.entity.Pet;
import com.kinglin.pet.dao.PetMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kinglin.pet.model.Result;
import com.kinglin.pet.model.vo.OwnerInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Resource
    private PetMapper petMapper;

    public Result<Pet> getInfo(Long id) {
        return Result.success(petMapper.selectById(id));
    }

    public Result<Long> add(Pet pet) {
        int insert = petMapper.insert(pet);
        if (insert > 0) {
            return Result.success(pet.getId());
        }
        return Result.error("添加宠物失败");
    }
}
