package com.kinglin.pet.controller;

import com.kinglin.pet.common.Constant;
import com.kinglin.pet.entity.Owner;
import com.kinglin.pet.model.Result;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangjl
 * @description 测试接口
 * @since 2023-05-12 16:52
 */
@RestController
@ApiModel("测试接口")
@RequestMapping(Constant.API_NAME + "/test")
public class TestController {

    @GetMapping
    @ApiOperation("测试异常处理")
    public Result<?> getOwnerInfo() {

        int result = 1 / 0;

        Owner owner = null;

        owner.getId();

        return Result.success();

    }

}
