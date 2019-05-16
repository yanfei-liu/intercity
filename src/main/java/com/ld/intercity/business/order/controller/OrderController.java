package com.ld.intercity.business.order.controller;

import com.github.pagehelper.PageInfo;
import com.ld.intercity.business.order.model.OrderModel;
import com.ld.intercity.business.order.service.OrderService;
import com.ld.intercity.utils.yaml.YamlPageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Api(value = "订单接口", tags = "订单相关操作接口")
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @ApiOperation(value = "订单生成")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public int save(@RequestBody OrderModel orderModel
            ,HttpServletRequest request){
        return service.save(orderModel,request);
    }


    /**
     * 删除订单
     * @param orderSn  订单编号
     * @return int
     */
    @ApiOperation(value = "删除订单")
    @RequestMapping(value = "/del",method = RequestMethod.GET)
    @ResponseBody
    public int del(@RequestParam String orderSn){
        return service.del(orderSn);
    }

    /**
     * 接单
     * @param orderSn 订单编号
     * @return int
     */
    @ApiOperation(value = "接单")
    @RequestMapping(value = "/updateJieDan",method = RequestMethod.GET)
    @ResponseBody
    public int updateJieDan(@RequestParam String orderSn, HttpServletRequest request){
        return service.updateJieDan(orderSn,request);
    }


    /**
     * 完成订单
     * @param orderSn 订单编号
     * @return int
     */
    @ApiOperation(value = "完成订单")
    @RequestMapping(value = "/updateWanCheng",method = RequestMethod.GET)
    @ResponseBody
    public int updateWanCheng(@RequestParam String orderSn){
        OrderModel oneByOrderSn = service.getOneByOrderSn(orderSn);
        oneByOrderSn.setOrderType("3");
        return service.update(oneByOrderSn);
    }

    /**
     * 修改订单
     * @param orderModel 订单实体类
     * @return int
     */
    @ApiOperation(value = "修改订单")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public int update(@RequestBody OrderModel orderModel){
        return service.update(orderModel);
    }

    /**
     * 查询全部订单
     * @return list
     */
    @ApiOperation(value = "查询全部订单")
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
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
    @RequestMapping(value = "/findAllByType",method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<OrderModel> findAllByType(@ApiParam(value = "当前页数", required = true)
                                              @PathVariable("pageNow") int pageNow,
                                              @RequestParam("type") String type){
        return service.findAllByType(pageNow, YamlPageUtils.getPageSize(),type);
    }

    /**
     * 根据订单号查询单个订单
     * @param orderSn 订单号
     * @return OrderModel
     */
    @ApiOperation(value = "根据订单号查询单个订单")
    @RequestMapping(value = "/getOneByOrderSn",method = RequestMethod.GET)
    @ResponseBody
    public OrderModel getOneByOrderSn(String orderSn){
        return service.getOneByOrderSn(orderSn);
    }
}
