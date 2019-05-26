package com.ld.intercity.business.order.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.ld.intercity.business.order.model.OrderModel;
import com.ld.intercity.business.order.service.OrderService;
import com.ld.intercity.business.user.model.UserModel;
import com.ld.intercity.utils.yaml.YamlPageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
    public String save(@RequestBody OrderModel orderModel
            ,HttpServletRequest request){
        HashMap<String, String> map = new HashMap<>();
        int save = service.save(orderModel, request);
        if (save==1){
            map.put("success","true");
        }else {
            map.put("success","false");
        }
        Gson gson = new Gson();
        return gson.toJson(map);
    }


    /**
     * 删除订单
     * @param orderSn  订单编号
     * @return int
     */
    @ApiOperation(value = "删除订单")
    @RequestMapping(value = "/del",method = RequestMethod.GET)
    @ResponseBody
    public String del(@RequestParam String orderSn){
        HashMap<String, String> map = new HashMap<>();
        int del = service.del(orderSn);
        if (del==1){
            map.put("success","true");
        }else {
            map.put("success","false");
        }
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    /**
     * 接单
     * @param orderSn 订单编号
     * @return int
     */
    @ApiOperation(value = "接单")
    @RequestMapping(value = "/updateJieDan",method = RequestMethod.GET)
    @ResponseBody
    public String updateJieDan(@RequestParam String orderSn, HttpServletRequest request){
        HashMap<String, String> map = new HashMap<>();
        int upd = service.updateJieDan(orderSn,request);
        if (upd==1){
            map.put("success","true");
        }else {
            map.put("success","false");
        }
        Gson gson = new Gson();
        return gson.toJson(map);
    }


    /**
     * 完成订单
     * @param orderSn 订单编号
     * @return int
     */
    @ApiOperation(value = "完成订单")
    @RequestMapping(value = "/updateWanCheng",method = RequestMethod.GET)
    @ResponseBody
    public String updateWanCheng(@RequestParam String orderSn){
        HashMap<String, String> map = new HashMap<>();
        OrderModel oneByOrderSn = service.getOneByOrderSn(orderSn);
        oneByOrderSn.setOrderType("3");
        int update = service.update(oneByOrderSn);
        if (update==1){
            map.put("success","true");
        }else {
            map.put("success","false");
        }
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    /**
     * 修改订单
     * @param orderModel 订单实体类
     * @return int
     */
    @ApiOperation(value = "修改订单")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(@RequestBody OrderModel orderModel){
        HashMap<String, String> map = new HashMap<>();
        int update = service.update(orderModel);
        if (update==1){
            map.put("success","true");
        }else {
            map.put("success","false");
        }
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    /**
     * 查询全部订单
     * @return list
     */
    @ApiOperation(value = "查询全部订单")
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ResponseBody
    public String findAll(){
        Gson gson = new Gson();
        List<OrderModel> all = service.findAll();
        String s = gson.toJson(all);
        return s;
    }

    /**
     * 根据传入状态查询各状态的全部订单
     * @param type 类型：0-已下单未接单 1-未开始已接单  2-已开始未结算  3-已结束未结算 4-已结束已结算  5-已取消
     * @return
     */
    @ApiOperation(value = "根据传入状态查询各状态的全部订单")
    @RequestMapping(value = "/findAllByType",method = RequestMethod.GET)
    @ResponseBody
    public String findAllByType(@ApiParam(value = "当前页数", required = true)
                                              @PathVariable("pageNow") int pageNow,
                              @ApiParam(value = "0-已下单未接单 1-未开始已接单  2-已开始未结算  3-已结束未结算 4-已结束已结算  5-已取消",required = true)
                              @PathVariable("type") String type){
        PageInfo<OrderModel> allByType = service.findAllByType(pageNow, YamlPageUtils.getPageSize(), type);
        Gson gson = new Gson();
        String s = gson.toJson(allByType);
        return s;
    }

    /**
     * 根据订单号查询单个订单
     * @param orderSn 订单号
     * @return OrderModel
     */
    @ApiOperation(value = "根据订单号查询单个订单")
    @RequestMapping(value = "/getOneByOrderSn",method = RequestMethod.GET)
    @ResponseBody
    public String getOneByOrderSn(String orderSn){
        OrderModel oneByOrderSn = service.getOneByOrderSn(orderSn);
        Gson gson = new Gson();
        String s = gson.toJson(oneByOrderSn);
        return s;
    }

    /**
     * 根据用户ID查询客户未结算订单或者司机接单未完成订单
     * @param request request
     * @return String
     */
    @ApiOperation(value = "根据用户ID查询客户未结算订单或者司机接单未完成订单")
    @RequestMapping(value = "/getByUserId",method = RequestMethod.GET)
    @ResponseBody
    public String getByUserId(HttpServletRequest request){
        UserModel user = (UserModel) request.getSession().getAttribute("user");
        //客户，查看其生成的当前未结算订单
        String identity = user.getIdentity();
        String s = "";
        Gson gson = new Gson();
        if ("1".equals(identity)){
            OrderModel byKeHuUserId = service.getByKeHuUserId(identity);
            s = gson.toJson(byKeHuUserId);
        }
        //司机，查看其接单的未结算订单
        if ("2".equals(identity)){
            List<OrderModel> bySiJiUserId = service.getBySiJiUserId(identity);
            s = gson.toJson(bySiJiUserId);
        }
        return s;
    }
}
