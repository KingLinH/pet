package com.kinglin.pet.service.impl;

import com.kinglin.pet.entity.Owner;
import com.kinglin.pet.dao.OwnerMapper;
import com.kinglin.pet.model.Result;
import com.kinglin.pet.model.vo.OwnerInfoVO;
import com.kinglin.pet.service.OwnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@Service
public class OwnerServiceImpl extends ServiceImpl<OwnerMapper, Owner> implements OwnerService {

    @Autowired
    OwnerMapper ownerMapper;

    @Override
    public Result<OwnerInfoVO> getOwnerInfo(String ownerId) {
        return Result.success(ownerMapper.getOwnerInfo(ownerId));
    }
}
