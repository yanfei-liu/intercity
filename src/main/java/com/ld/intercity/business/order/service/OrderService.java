package com.ld.intercity.business.order.service;

import com.ld.intercity.business.order.model.OrderModel;

import java.util.List;

public interface OrderService {
    /**
     * 生成订单
     * @param orderModel 订单实体类
     * @return int
     */
    int save(OrderModel orderModel);

    /**
     * 删除订单
     * @param orderSn  订单编号
     * @return int
     */
    int del(String orderSn);

    /**
     * 修改订单
     * @param orderModel 订单实体类
     * @return int
     */
    int update(OrderModel orderModel);

    /**
     * 查询全部订单
     * @return list
     */
    List<OrderModel> findAll();

    /**
     * 根据传入状态查询各状态的全部订单
     * @param type 类型：0-已下单未接单 1-未开始已接单  2-已开始未结算 3-已结束已结算   4-已取消
     * @return
     */
    List<OrderModel> findAllByType(String type);

    /**
     * 根据订单号查询单个订单
     * @param orderSn 订单号
     * @return OrderModel
     */
    OrderModel getOneByOrderSn(String orderSn);


}
