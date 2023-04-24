package com.jinli.jinrui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinli.jinrui.dto.DishDto;
import com.jinli.jinrui.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息进行删除
    public void removeWithFlavor(List<Long> ids);

    //根据id数量来修改菜品信息的状态
    public void updateWithStatus(List<Long> params);

}
