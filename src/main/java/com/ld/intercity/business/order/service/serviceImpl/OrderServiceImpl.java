package com.ld.intercity.business.order.service.serviceImpl;

import com.ld.intercity.business.order.dao.OrderMapper;
import com.ld.intercity.business.order.model.OrderModel;
import com.ld.intercity.business.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public int save(OrderModel orderModel) {
        return orderMapper.save(orderModel);
    }
}
