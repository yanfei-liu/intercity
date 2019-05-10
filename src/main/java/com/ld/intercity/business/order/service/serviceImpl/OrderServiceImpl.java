package com.ld.intercity.business.order.service.serviceImpl;

import com.ld.intercity.business.order.dao.OrderMapper;
import com.ld.intercity.business.order.model.OrderModel;
import com.ld.intercity.business.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    @Transactional
    public int save(OrderModel orderModel) {
        return orderMapper.save(orderModel);
    }

    @Override
    @Transactional
    public int del(String orderSn) {
        return orderMapper.del(orderSn);
    }

    @Override
    @Transactional
    public int update(OrderModel orderModel) {
        return orderMapper.update(orderModel);
    }

    @Override
    public List<OrderModel> findAll() {
        return orderMapper.findAll();
    }

    @Override
    public List<OrderModel> findAllByType(String type) {
        return orderMapper.findAllByType(type);
    }

    @Override
    public OrderModel getOneByOrderSn(String orderSn) {
        return orderMapper.getOneByOrderSn(orderSn);
    }
}
