package com.jinli.jinrui.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinli.jinrui.common.BaseContext;
import com.jinli.jinrui.common.Result;
import com.jinli.jinrui.dto.OrdersDto;
import com.jinli.jinrui.entity.OrderDetail;
import com.jinli.jinrui.entity.Orders;
import com.jinli.jinrui.entity.ShoppingCart;
import com.jinli.jinrui.service.OrderDetailService;
import com.jinli.jinrui.service.OrderService;
import com.jinli.jinrui.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return Result.success("下单成功");
    }


    /**
     * 后台查询订单明细
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String number,String beginTime,String endTime){
        //分页构造器对象
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        //构造条件查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();

        //添加查询条件  动态sql  字符串使用StringUtils.isNotEmpty这个方法来判断
        //这里使用了范围查询的动态SQL，这里是重点！！！
        queryWrapper.like(number!=null,Orders::getNumber,number)
                .ge(StringUtils.isNotEmpty(beginTime),Orders::getOrderTime,beginTime)
                .le(StringUtils.isNotEmpty(endTime),Orders::getOrderTime,endTime);

        orderService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }

    /**
     * 用户订单分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public Result<Page> page(int page, int pageSize){

        //分页构造器对象
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        Page<OrdersDto> pageDto = new Page<>(page,pageSize);

        //构造条件查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());

        //添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(Orders::getOrderTime);
        orderService.page(pageInfo,queryWrapper);

        //通过OrderId查询对应的OrderDeteail
        LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //对OrderDto进行需要的属性赋值
        List<Orders> records = pageInfo.getRecords();
        List<OrdersDto> ordersDtoList = records.stream().map((item) -> {
            OrdersDto ordersDto = new OrdersDto();
            //获取订单id
            Long orderId = item.getId();

            List<OrderDetail> orderDetailList = orderService.getOrderDetailById(orderId);
            BeanUtils.copyProperties(item, ordersDto);
            //对orderDto进行属性的赋值
            ordersDto.setOrderDetails(orderDetailList);
            return ordersDto;
        }).collect(Collectors.toList());

        BeanUtils.copyProperties(pageInfo, pageDto, "records");
        pageDto.setRecords(ordersDtoList);
        return Result.success(pageDto);
    }

    /**
     * 再来一单
     * @param map
     * @return
     */
    @PostMapping("/again")
    public Result<String> againSubmit(@RequestBody Map<String,String> map){
        String ids = map.get("id");
        long id = Long.parseLong(ids);

        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId, id);
        //获取该订单对应的所有的订单明细
        List<OrderDetail> orderDetailsList = orderDetailService.list(queryWrapper);

        //通过用户id把原来的购物车数据清空
        LambdaQueryWrapper<ShoppingCart> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(queryWrapper1);

        //获取用户id
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCartsList = orderDetailsList.stream().map((item)->{
            //把从order表中和order_details表中获取到的数据赋值给这个购物车对象
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(userId);
            shoppingCart.setImage(item.getImage());

            Long dishId = item.getDishId();
            Long setmealId = item.getSetmealId();
            if(dishId != null){
                //如果是菜品那就加菜品的查询条件
                shoppingCart.setDishId(dishId);
            }else{
                //如果是菜品那就加菜品的查询条件
                shoppingCart.setSetmealId(setmealId);
            }

            shoppingCart.setName(item.getName());
            shoppingCart.setDishFlavor(item.getDishFlavor());
            shoppingCart.setNumber(item.getNumber());
            shoppingCart.setAmount(item.getAmount());
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;
        }).collect(Collectors.toList());

        //把携带数据的购物车批量插入到购物车表中
        shoppingCartService.saveBatch(shoppingCartsList);

        return Result.success("操作成功");
    }

    @PutMapping
    public Result<String> update(Long id){

        return null;
    }


}
