package com.jinli.jinrui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinli.jinrui.common.BaseContext;
import com.jinli.jinrui.common.Result;
import com.jinli.jinrui.entity.ShoppingCart;
import com.jinli.jinrui.mapper.ShoppingCartMapper;
import com.jinli.jinrui.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    /*@Autowired
    private ShoppingCartService shoppingCartService;

    @Override
    public Result<String> clean() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper);

        return Result.success("清空购物车成功");
    }*/
}
