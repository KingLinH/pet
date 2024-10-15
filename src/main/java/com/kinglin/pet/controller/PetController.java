package com.kinglin.pet.controller;

import com.kinglin.pet.common.Constant;
import com.kinglin.pet.entity.Owner;
import com.kinglin.pet.entity.Pet;
import com.kinglin.pet.model.Result;
import com.kinglin.pet.service.PetServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 宠物信息表 前端控制器
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@RestController
@RequestMapping(Constant.API_NAME + "/pet")
public class PetController {

    @Resource
    private PetServiceImpl petService;

    @GetMapping
    @ApiModelProperty(value = "根据id查询宠物信息")
    @ApiImplicitParam(value = "id", name = "id", required = true)
    public Result<Pet> getInfo(Long id) {
        return petService.getInfo(id);
    }

    @PostMapping
    @ApiModelProperty(value = "添加宠物信息")
    @ApiImplicitParam(value = "宠物信息", name = "pet", required = true)
    public Result<Long> add(Pet pet) {
        return petService.add(pet);
    }

    @PutMapping
    @ApiModelProperty(value = "修改宠物信息")
    @ApiImplicitParam(value = "宠物信息", name = "pet", required = true)
    public Result<Boolean> updateById(Pet pet) {
        return Result.success(petService.updateById(pet));
    }

}
