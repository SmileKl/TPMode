package com.example.vegetables.service.impl;

import com.example.vegetables.model.OrderCommodity;
import com.example.vegetables.dao.OrderCommodityMapper;
import com.example.vegetables.service.IOrderCommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单-商品关联表 服务实现类
 * </p>
 *
 * @author 朱归华
 * @since 2022-04-28
 */
@Service
public class OrderCommodityServiceImpl extends ServiceImpl<OrderCommodityMapper, OrderCommodity> implements IOrderCommodityService {

}
