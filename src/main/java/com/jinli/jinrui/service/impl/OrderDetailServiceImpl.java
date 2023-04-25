package com.jinli.jinrui.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinli.jinrui.entity.OrderDetail;
import com.jinli.jinrui.mapper.OrderDetailMapper;
import com.jinli.jinrui.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
