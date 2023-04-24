package com.jinli.jinrui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinli.jinrui.common.CustomException;
import com.jinli.jinrui.dto.DishDto;
import com.jinli.jinrui.entity.Dish;
import com.jinli.jinrui.entity.DishFlavor;
import com.jinli.jinrui.mapper.DishMapper;
import com.jinli.jinrui.service.DishFlavorService;
import com.jinli.jinrui.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /***
     * 新增菜品信息
     * @param dishDto
     */
    @Override
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

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息,从dish表查询
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);

        //查询当前菜品对应的口味信息,从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    /***
     * 修改菜品信息
     * @param dishDto
     */
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //关系dish表基本信息
        this.updateById(dishDto);

        //清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        //添加当前提交过来的口味数据---dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    /***
     * 删除菜品，同时删除需要删除菜品和口味关联的数据
     * @param ids
     */
    @Override
    @Transactional
    public void removeWithFlavor(List<Long> ids) {
        //查询菜品状态，判断是否能够删除
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //设置查询条件,判断选择到的数据状态是否为1
        queryWrapper.in(Dish::getId, ids);
        queryWrapper.eq(Dish::getStatus, 1);
        //使用count来接收符合条件的数据
        int count = this.count(queryWrapper);
        //如果有符合条件的数据，就会抛出一个业务异常
        if(count > 0 ){
            throw new CustomException("该商品正在售卖中，不能删除");
        }

        //如果可以删除，将删除套餐里面的数据（可批量删除）
        this.removeByIds(ids);
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(DishFlavor::getDishId, ids);

        dishFlavorService.remove(lambdaQueryWrapper);
    }

    /***
     * 根据id数量来修改菜品信息的状态
     * @param params
     */
    @Override
    public void updateWithStatus(List<Long> params) {

    }


}
