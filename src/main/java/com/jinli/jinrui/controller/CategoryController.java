package com.jinli.jinrui.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinli.jinrui.common.Result;
import com.jinli.jinrui.entity.Category;
import com.jinli.jinrui.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Category category){
        //log.info("category:{}",category);
        //创建时间
        category.setCreateTime(LocalDateTime.now());
        //修改时间
        category.setUpdateTime(LocalDateTime.now());
        //获取当前登陆的id
        Long empId =(Long) request.getSession().getAttribute("category");
        //创建人
        category.setCreateUser(empId);
        //修改人
        category.setUpdateUser(empId);
        categoryService.save(category);
        return Result.success("新增分类成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page,int pageSize){
        log.info("page:{}",page);
        log.info("pageSize:{}",pageSize);
        //分页构造器
        Page<Category> pageInfo = new Page<>(page,pageSize);
        log.info("pageInfo:{}",pageInfo);
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(Category::getSort);

        //分页查询
        categoryService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public Result<String> delete(Long id){
        log.info("删除分类，id为：{}",id);

        categoryService.removeById(id);
        //categoryService.remove(id);

        return Result.success("分类信息删除成功");
    }

    /**
     * 根据id修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody Category category){
        log.info("修改分类信息：{}",category);

        categoryService.updateById(category);

        return Result.success("修改分类信息成功");
    }


}
