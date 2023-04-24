package com.jinli.jinrui.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinli.jinrui.common.Result;
import com.jinli.jinrui.dto.DishDto;
import com.jinli.jinrui.entity.Category;
import com.jinli.jinrui.entity.Dish;
import com.jinli.jinrui.service.CategoryService;
import com.jinli.jinrui.service.DishFlavorService;
import com.jinli.jinrui.service.DishService;
import com.sun.javafx.logging.PulseLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/***
 * 菜品管理
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /***
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody DishDto dishDto){
        //log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);
        return Result.success("新增菜品成功");
    }

    /***
     * 修改菜品信息
     * @param dishDto
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto){

        dishService.updateWithFlavor(dishDto);
        return Result.success("修改菜品成功");
    }

    /***
     * 删除菜品信息
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> delete(@RequestParam List<Long> ids){
        //普通删除
        //dishService.removeById(ids);

        dishService.removeWithFlavor(ids);

        return Result.success("菜品信息删除成功");
    }

    /***
     * 根据id数量来修改菜品信息的状态
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<String> updateStatus(@PathVariable Integer status,Long[] ids){
        //log.info("停售起售====>"+ status);
        //log.info("停售起售====>"+ ids);

        //通过id查询数据库，修改id为ids数组中数据的菜品状态，status为前端页面提交的status
        for (int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            //根据id得到每个dish菜品
            Dish dish = dishService.getById(id);
            dish.setStatus(status);
            dishService.updateById(dish);
        }
        return Result.success("修改菜品状态成功");
    }

    /***
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name){
        //构建分页构造器对象
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null , Dish::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo, queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage,"records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            //把item拷贝到dishDto里面去
            BeanUtils.copyProperties(item, dishDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return Result.success(dishDtoPage);
    }

    /***
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return Result.success(dishDto);
    }


    /***
     * 根据条件查询对应的菜品数据
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public Result<List<Dish>> list(Dish dish) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        //查询状态为1（起售）的数据
        queryWrapper.eq(Dish::getStatus, 1);

        //添加排序条件
        queryWrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        return Result.success(list);
    }

}
