package com.kinglin.pet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kinglin.pet.entity.Owner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@Mapper
public interface OwnerMapper extends BaseMapper<Owner> {
    Owner getByDisplayName(@Param("displayName") String displayName);
}
