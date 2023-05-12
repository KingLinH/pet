package com.kinglin.pet.controller;

import com.kinglin.pet.common.Constant;
import com.kinglin.pet.model.Result;
import com.kinglin.pet.model.vo.OwnerInfoVO;
import com.kinglin.pet.service.OwnerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    OwnerService ownerService;

    @GetMapping
    @ApiModelProperty(value = "根据用户id查询用户信息")
    @ApiImplicitParam(value = "用户id", name = "ownerId", required = true)
    public Result<OwnerInfoVO> getOwnerInfo(String ownerId) {
        return ownerService.getOwnerInfo(ownerId);
    }

}
