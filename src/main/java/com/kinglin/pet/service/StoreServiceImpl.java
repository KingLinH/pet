package com.kinglin.pet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kinglin.pet.dao.StoreMapper;
import com.kinglin.pet.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 门店信息表 服务实现类
 * </p>
 *
 * @author huangjl
 * @since 2023-05-12
 */
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements IService<Store> {

    @Autowired
    private StoreMapper storeMapper;


}
