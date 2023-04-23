package com.jinli.jinrui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinli.jinrui.dto.DishDto;
import com.jinli.jinrui.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);



}
