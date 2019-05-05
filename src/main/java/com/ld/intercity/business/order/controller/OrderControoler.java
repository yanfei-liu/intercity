package com.ld.intercity.business.order.controller;

import com.ld.intercity.business.order.model.OrderModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderControoler {

    @ApiOperation(value = "订单生成")
    @RequestMapping(value = "save")
    public String save(OrderModel orderModel){
        return null;
    }
}
