package com.kinglin.pet.controller;

import com.kinglin.pet.common.Constant;
import com.kinglin.pet.service.StoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 门店信息表 前端控制器
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@RestController
@RequestMapping(Constant.API_NAME + "/store")
public class StoreController {

    @Autowired
    private StoreServiceImpl storeService;

}
