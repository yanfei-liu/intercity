package com.ld.intercity.business.order.controller;

import com.ld.intercity.business.order.model.OrderModel;
import com.ld.intercity.business.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderService service;

    @ApiOperation(value = "订单生成")
    @RequestMapping(value = "save")
    @ResponseBody
    public int save(OrderModel orderModel){
        return service.save(orderModel);
    }


    /**
     * 删除订单
     * @param orderSn  订单编号
     * @return int
     */
    @ApiOperation(value = "删除订单")
    @RequestMapping("del")
    @ResponseBody
    public int del(String orderSn){
        return service.del(orderSn);
    }

    /**
     * 修改订单
     * @param orderModel 订单实体类
     * @return int
     */
    @ApiOperation(value = "修改订单")
    @RequestMapping("update")
    @ResponseBody
    public int update(OrderModel orderModel){
        return service.update(orderModel);
    }

    /**
     * 查询全部订单
     * @return list
     */
    @ApiOperation(value = "查询全部订单")
    @RequestMapping("findAll")
    @ResponseBody
    public List<OrderModel> findAll(){
        return service.findAll();
    }

    /**
     * 根据传入状态查询各状态的全部订单
     * @param type 类型：0-已下单未接单 1-未开始已接单  2-已开始未结算 3-已结束已结算   4-已取消
     * @return
     */
    @ApiOperation(value = "根据传入状态查询各状态的全部订单")
    @RequestMapping("findAllByType")
    @ResponseBody
    public List<OrderModel> findAllByType(String type){
        return service.findAllByType(type);
    }

    /**
     * 根据订单号查询单个订单
     * @param orderSn 订单号
     * @return OrderModel
     */
    @ApiOperation(value = "根据订单号查询单个订单")
    @RequestMapping("getOneByOrderSn")
    @ResponseBody
    public OrderModel getOneByOrderSn(String orderSn){
        return service.getOneByOrderSn(orderSn);
    }
}
