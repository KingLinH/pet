package com.kinglin.pet.controller;

import com.kinglin.pet.common.Constant;
import com.kinglin.pet.entity.Owner;
import com.kinglin.pet.model.Result;
import com.kinglin.pet.model.vo.OwnerInfoVO;
import com.kinglin.pet.service.OwnerServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@RestController
@ApiModel("用户")
@RequestMapping(Constant.API_NAME + "/owner")
public class OwnerController {
    @Resource
    OwnerServiceImpl ownerServiceImpl;

    @GetMapping
    @ApiModelProperty(value = "根据用户id查询用户信息")
    @ApiImplicitParam(value = "用户id", name = "ownerId", required = true)
    public Result<OwnerInfoVO> getInfo(Long ownerId) {
        return ownerServiceImpl.getInfo(ownerId);
    }

    @PostMapping
    @ApiModelProperty(value = "新增用户信息")
    @ApiImplicitParam(value = "用户信息", name = "owner", required = true)
    public Result<Long> add(Owner owner) {
        return ownerServiceImpl.add(owner);
    }

    @PutMapping
    @ApiModelProperty(value = "修改用户信息")
    @ApiImplicitParam(value = "用户信息", name = "owner", required = true)
    public Result<Boolean> updateById(Owner owner) {
        return Result.success(ownerServiceImpl.updateById(owner));
    }

    @PostMapping("/update/password")
    @ApiModelProperty(value = "修改用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "id", name = "id", required = true),
            @ApiImplicitParam(value = "旧密码", name = "orgPassword", required = true),
            @ApiImplicitParam(value = "新密码", name = "newPassword", required = true)
    })
    public Result<Integer> updatePassword(Long id, String orgPassword, String newPassword) {
        return ownerServiceImpl.updatePassword(id, orgPassword, newPassword);
    }

}
