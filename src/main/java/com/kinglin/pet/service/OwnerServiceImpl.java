package com.kinglin.pet.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kinglin.pet.dao.OwnerMapper;
import com.kinglin.pet.entity.Owner;
import com.kinglin.pet.enums.GenderEnum;
import com.kinglin.pet.model.LoginUser;
import com.kinglin.pet.model.Result;
import com.kinglin.pet.model.vo.OwnerInfoVO;
import com.kinglin.pet.util.MD5Util;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class OwnerServiceImpl extends ServiceImpl<OwnerMapper, Owner> implements IService<Owner>, UserDetailsService {

    @Resource
    OwnerMapper ownerMapper;
    @Lazy
    @Resource
    PasswordEncoder passwordEncoder;

    public Result<OwnerInfoVO> getInfo(Long ownerId) {
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
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        int insert = ownerMapper.insert(owner);
        if (insert > 0) {
            return Result.success(owner.getId());
        }
        return Result.error("添加用户失败");
    }

    public Result<Integer> updatePassword(Long id, String orgPassword, String newPassword) {
        Owner owner = ownerMapper.selectById(id);
        if (!owner.getPassword().equals(passwordEncoder.encode(orgPassword))) {
            Result.error("原密码错误");
        }
        Owner updateOwner = new Owner();
        updateOwner.setId(owner.getId());
        updateOwner.setPassword(passwordEncoder.encode(newPassword));
        return Result.success(ownerMapper.updateById(updateOwner));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Owner> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Owner owner = baseMapper.selectOne(queryWrapper);
        if (Objects.isNull(owner)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(owner, loginUser);
        loginUser.setOwner(owner);
        return loginUser;
    }
}
