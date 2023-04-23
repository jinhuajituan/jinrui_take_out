package com.jinli.jinrui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinli.jinrui.dto.DishDto;
import com.jinli.jinrui.entity.Dish;
import com.jinli.jinrui.entity.DishFlavor;
import com.jinli.jinrui.mapper.DishMapper;
import com.jinli.jinrui.service.DishFlavorService;
import com.jinli.jinrui.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    //开启事务控制，还需要在启动类添加@EnableTransactionManagement注解
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品dish
        this.save(dishDto);

        //菜品id
        Long dishId = dishDto.getId();

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) ->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);
    }


}
