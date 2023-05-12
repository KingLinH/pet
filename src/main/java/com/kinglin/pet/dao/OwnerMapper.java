package com.kinglin.pet.dao;

import com.kinglin.pet.entity.Owner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kinglin.pet.model.vo.OwnerInfoVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
public interface OwnerMapper extends BaseMapper<Owner> {

    OwnerInfoVO getOwnerInfo(@Param("ownerId") String ownerId);
}
