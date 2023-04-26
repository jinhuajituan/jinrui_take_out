package com.jinli.jinrui.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinli.jinrui.dto.SetmealDto;
import com.jinli.jinrui.entity.OrderDetail;
import com.jinli.jinrui.entity.Orders;

import java.util.List;

public interface OrderService extends IService<Orders> {
    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders);

    /***
     * 通过Id查询数据
     * @param orderId
     * @return
     */
    public List<OrderDetail> getOrderDetailById (Long orderId);
}
