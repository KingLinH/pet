package com.kinglin.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kinglin.pet.dao.OwnerMapper;
import com.kinglin.pet.entity.Owner;
import com.kinglin.pet.enums.GenderEnum;
import com.kinglin.pet.model.Result;
import com.kinglin.pet.model.vo.OwnerInfoVO;
import com.kinglin.pet.util.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@Service
public class OwnerServiceImpl extends ServiceImpl<OwnerMapper, Owner> implements IService<Owner> {

    @Autowired
    OwnerMapper ownerMapper;

    public Result<OwnerInfoVO> getOwnerInfo(Long ownerId) {
        Owner owner = ownerMapper.selectById(ownerId);
        OwnerInfoVO ownerInfoVO = new OwnerInfoVO();
        if (owner != null) {
            BeanUtils.copyProperties(owner, ownerInfoVO);
        }
        return Result.success(ownerInfoVO);
    }

    public Result<Long> add(Owner owner) {
        if (owner == null) {
            return Result.error("参数为空");
        }
        // TODO 校验
        owner.setGender(GenderEnum.enumByName(owner.getGender()).getValue());
        owner.setSalt(MD5Util.getSalt(32));
        owner.setPassword(MD5Util.encode(MD5Util.encode(owner.getPassword(), owner.getSalt()), owner.getSalt()));
        int insert = ownerMapper.insert(owner);
        if (insert > 0) {
            return Result.success(owner.getId());
        }
        return Result.error("新增用户失败");
    }

    public Result<OwnerInfoVO> login(String displayName, String password) {
        Owner owner = ownerMapper.getByDisplayName(displayName);
        if (Objects.isNull(owner)) {
            return Result.error("用户不存在");
        }
        if (MD5Util.encode(MD5Util.encode(password, owner.getSalt()), owner.getSalt()).equals(owner.getPassword())) {
            OwnerInfoVO ownerInfoVO = new OwnerInfoVO();
            BeanUtils.copyProperties(owner, ownerInfoVO);
            return Result.success(ownerInfoVO);
        } else {
            return Result.error("用户名或密码错误");
        }
    }
}
