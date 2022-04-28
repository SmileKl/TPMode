package com.example.vegetables.service.impl;

import com.example.vegetables.model.Order;
import com.example.vegetables.dao.OrderMapper;
import com.example.vegetables.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author 朱归华
 * @since 2022-04-28
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
