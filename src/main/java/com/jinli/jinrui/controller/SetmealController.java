package com.jinli.jinrui.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinli.jinrui.common.Result;
import com.jinli.jinrui.dto.DishDto;
import com.jinli.jinrui.dto.SetmealDto;
import com.jinli.jinrui.entity.Category;
import com.jinli.jinrui.entity.Dish;
import com.jinli.jinrui.entity.Setmeal;
import com.jinli.jinrui.entity.SetmealDish;
import com.jinli.jinrui.service.CategoryService;
import com.jinli.jinrui.service.DishService;
import com.jinli.jinrui.service.SetmealDishService;
import com.jinli.jinrui.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/*
* 套餐管理
* */
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody SetmealDto setmealDto){
        //log.info(setmealDto.toString());
        setmealService.saveWithDish(setmealDto);

        return Result.success("新增套餐成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize  ,String name){
        //分页构造器对象
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //条件通过那么进行模糊查询
        queryWrapper.like(name != null, Setmeal::getName, name);
        //根据修改时间进行倒序排序
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list =  records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item,setmealDto);

            //分类ID
            Long categoryId = item.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(category != null){
                //分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return Result.success(dtoPage);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> delete (@RequestParam List<Long> ids){
        //log.info("ids:{}", ids);
        setmealService.removeWithDish(ids);

        return Result.success("删除套餐");
    }

    /**
     * 根据id查询套餐信息
     *(套餐信息的回显)
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SetmealDto> getById(@PathVariable Long id) {
        log.info("根据id查询套餐信息:{}", id);
        // 调用service执行查询。
        SetmealDto setmealDto = setmealService.getByIdWithDish(id);
        return Result.success(setmealDto);
    }

    /**
     * 修改套餐信息。
     * @param setmealDto
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody SetmealDto setmealDto) {
        log.info("修改套餐信息{}", setmealDto);
        // 执行更新。
        setmealService.updateWithDish(setmealDto);
        return Result.success("修改套餐信息成功");
    }

    /**
     * 套餐的停售与启售
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<String> uptateStatus(@PathVariable Integer status, Long[] ids){
        log.info("status", status);
        log.info("ids", ids);

        //通过id查询数据库，修改id为ids数组中数据的菜品状态，status为前端页面提交的status
        for (int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            //根据id得到每个dish菜品
            Setmeal setmeal = setmealService.getById(id);
            setmeal.setStatus(status);
            setmealService.updateById(setmeal);
        }
        return Result.success("修改套餐状态成功");
    }

    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public Result<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(queryWrapper);

        return Result.success(list);
    }

    /**
     * 根据条件查询对应的套餐数据（手机端点击查看）
     * @param Setmealid
     * @return
     */
    @GetMapping("/dish/{id}")
    public Result<List<DishDto>> dish(@PathVariable("id") Long Setmealid){
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, Setmealid);
        //获取套餐里面的所有菜品，这个就是SetmealDish表里面的数据
        List<SetmealDish> list = setmealDishService.list(queryWrapper);

        List<DishDto> dishDtos = list.stream().map((setmealDish) ->{
            DishDto dishDto = new DishDto();
            //浅拷贝
            BeanUtils.copyProperties(setmealDish, dishDto);
            //把套餐中的菜品基本信息填充到dto中
            Long dishId = setmealDish.getDishId();
            Dish dish = dishService.getById(dishId);
            BeanUtils.copyProperties(dish, dishDto);
            return dishDto;
        }).collect(Collectors.toList());
        return Result.success(dishDtos);
    }
}
