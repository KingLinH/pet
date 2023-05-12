package com.kinglin.pet.service;

import com.kinglin.pet.entity.Owner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kinglin.pet.model.Result;
import com.kinglin.pet.model.vo.OwnerInfoVO;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
public interface OwnerService extends IService<Owner> {

    Result<OwnerInfoVO> getOwnerInfo(String ownerId);
}
