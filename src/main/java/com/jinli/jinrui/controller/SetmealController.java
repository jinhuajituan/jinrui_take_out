package com.jinli.jinrui.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinli.jinrui.common.Result;
import com.jinli.jinrui.dto.SetmealDto;
import com.jinli.jinrui.entity.Category;
import com.jinli.jinrui.entity.Setmeal;
import com.jinli.jinrui.entity.SetmealDish;
import com.jinli.jinrui.service.CategoryService;
import com.jinli.jinrui.service.SetmealDishService;
import com.jinli.jinrui.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import java.awt.print.PageFormat;
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
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /***
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

    /***
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<String> delete (List<Long> ids){


        return Result.success("删除套餐");
    }

}
