package com.ld.intercity.business.order.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.ld.intercity.business.order.model.OrderModel;
import com.ld.intercity.business.order.service.OrderService;
import com.ld.intercity.business.user.model.UserModel;
import com.ld.intercity.business.user.service.UserService;
import com.ld.intercity.utils.ResponseResult;
import com.ld.intercity.utils.yaml.YamlPageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(value = "订单接口", tags = "订单相关操作接口")
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "订单查询")
    @RequestMapping(value = "/Init")
    @ResponseBody
    public ModelAndView Init(){
        return new ModelAndView("/pages/order/order.html");
    }

    @ApiOperation(value = "订单详情查询")
    @RequestMapping(value = "/Init2")
    @ResponseBody
    public ModelAndView Init2(){
        return new ModelAndView("/pages/order/orderDetails.html");
    }

    @ApiOperation(value = "订单生成")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public String save(@RequestBody OrderModel orderModel){
        Map save = service.save(orderModel);
        Gson gson = new Gson();
        return gson.toJson(save);
    }

    @ApiOperation(value = "根据指定条件进行订单查询")
    @RequestMapping(value = "/findOrderByQuery",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<List<OrderModel>> findOrderByQuery(
            @RequestParam("val") String val,
            @RequestParam("val1") String val1,
            @RequestParam("val2") String val2){
        ResponseResult<List<OrderModel>> orderByQuery = new ResponseResult<>();
        try {
            orderByQuery = service.findOrderByQuery(val, val1, val2);
        } catch (Exception e) {
            e.printStackTrace();
            orderByQuery.setSuccess(false);
        }
        return orderByQuery;
    }

    @ApiOperation(value = "根据指定条件进行订单查询-后端")
    @RequestMapping(value = "/findOrderByQueryBack",method = RequestMethod.GET)
    @ResponseBody
    public String findOrderByQueryBack(
            @RequestParam("val") String val,
            @RequestParam("val1") String val1,
            @RequestParam("val2") String val2,
            @RequestParam("limit")String limit,
            @RequestParam("offset")String offset,
            @RequestParam("page")String page){
        Gson gson = new Gson();
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        if (StringUtils.isBlank(val)&&StringUtils.isBlank(val1)&&StringUtils.isBlank(val2)){
            stringObjectHashMap.put("total",0);
            stringObjectHashMap.put("rows",new ArrayList<>());
        }else {
            try {
                ResponseResult<List<OrderModel>> orderByQuery = service.findOrderByQuery(val, val1, val2);
                if (orderByQuery.isSuccess()){
                    List<OrderModel> data = orderByQuery.getData();
                    int toIndex = Integer.parseInt(offset);
                    int fromIndex = Integer.parseInt(limit);
                    if (fromIndex > data.size()) {
                        fromIndex = data.size();
                    }
                    List<OrderModel> orderModels = data.subList(toIndex,fromIndex);
                    stringObjectHashMap.put("total",data.size());
                    stringObjectHashMap.put("rows",orderModels);
                }else {
                    stringObjectHashMap.put("total",0);
                    stringObjectHashMap.put("rows",new ArrayList<>());
                }
            } catch (Exception e) {
                e.printStackTrace();
                stringObjectHashMap.put("total",0);
                stringObjectHashMap.put("rows",new ArrayList<>());
            }
        }
        String s = gson.toJson(stringObjectHashMap);
        return s;
    }

    /**
     * 删除订单
     * @param orderSn  订单编号
     * @return json
     */
    @ApiOperation(value = "删除订单")
    @RequestMapping(value = "/del",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<String> del(@RequestParam String orderSn){
        return service.del(orderSn);
    }

    /**
     * 司机去接单大厅检索订单
     * @param routeId    路线ID
     * @param date    时间
     * @param pageNo    当前页数
     * @return  Re
     */
    @ApiOperation(value = "司机去接单大厅检索订单")
    @RequestMapping(value = "/findByDriverFindOrder",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<List<OrderModel>> findByDriverFindOrder(@RequestParam("routeId")String routeId,
                                        @RequestParam("date")String date,
                                        @RequestParam(value = "pageNo",required = false)String pageNo){
        ResponseResult<List<OrderModel>> listResponseResult = new ResponseResult<>();
        try {
            if (StringUtils.isBlank(pageNo)){
                pageNo = "1";
            }
            int i = Integer.parseInt(pageNo);
            int pageSize = YamlPageUtils.getPageSize();
            int i1 = (i - 1) * pageSize;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String s = date + " 00:00:00";
            String s1 = date + " 23:59:59";
            listResponseResult = service.findByDriverFindOrder(routeId, s, s1, i1, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listResponseResult;
    }

    /**
     * 接单
     * @param orderSn 订单编号
     * @return json
     */
    @ApiOperation(value = "接单")
    @RequestMapping(value = "/updateJieDan",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<String> updateJieDan(@RequestParam("orderSn") String orderSn,@RequestParam("uuid")String uuid){
        ResponseResult<String> stringResponseResult = new ResponseResult<>();
        int upd = service.updateJieDan(orderSn,uuid);
        if (upd==1){
            stringResponseResult.setSuccess(true);
            stringResponseResult.setMessage("接单成功");
        }else {
            stringResponseResult.setSuccess(false);
            stringResponseResult.setMessage("接单失败");
        }
        return stringResponseResult;
    }
    @ApiOperation(value = "接单记录")
    @RequestMapping(value = "/findOrderByDriver",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<List<OrderModel>> findOrderByDriver(@RequestParam("uuid") String uuid){
        return service.findOrderByDriver(uuid);
    }

    @ApiOperation(value = "查看司机接单后未完成的订单")
    @RequestMapping(value = "/findBySiJiUserId",method = RequestMethod.GET)
    @ResponseBody
    public List<OrderModel> findBySiJiUserId(@Param("uuid") String uuid){
        return service.getBySiJiUserId(uuid);
    }

    /**
     * 完成订单
     * @param orderSn 订单编号
     * @return json
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
            map.put("code","0");
            map.put("msg","订单已完成");
        }else {
            map.put("code","1001");
            map.put("msg","订单修改失败");
        }
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    /**
     * 订单结算
     * @param orderSn   订单编号
     * @return  ResponseResult
     */
    @ApiOperation(value = "订单结算")
    @RequestMapping(value = "/orderSetting",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<String> orderSetting(@RequestParam("orderSn") String orderSn){
        return service.orderSetting(orderSn);
    }

    /**
     * 修改订单
     * @param orderModel 订单实体类
     * @return json
     */
    @ApiOperation(value = "修改订单")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public String update(@RequestBody OrderModel orderModel){
        HashMap<String, String> map = new HashMap<>();
        OrderModel oneByOrderSn = service.getOneByOrderSn(orderModel.getOrderSn());
        if ("0".equals(oneByOrderSn.getOrderType())){
            int update = service.update(orderModel);
            if (update==1){
                map.put("code","0");
                map.put("msg","修改成功");
            }else {
                map.put("code","1001");
                map.put("msg","修改失败，请稍后再试");
            }
        }else {
            map.put("code","1002");
            map.put("msg","该订单已被接单不得进行修改");
        }

        Gson gson = new Gson();
        return gson.toJson(map);
    }

    /**
     * 查询全部订单
     * @return json
     */
    @ApiOperation(value = "查询全部订单")
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<List<OrderModel>> findAll(@RequestParam(value = "userId",required = false) String userId,
                          @RequestParam(value = "pageNo",required = false) String pageNo,
                          @RequestParam(value = "pageSize",required = false) String pageSize){
        ResponseResult<List<OrderModel>> stringResponseResult = new ResponseResult<>();
        stringResponseResult.setSuccess(true);
        if (StringUtils.isBlank(pageNo)){
            pageNo = "0";
        }
        if (StringUtils.isBlank(pageSize)){
            pageSize = "10";
        }
        int i = Integer.parseInt(pageNo);
        int i2 = Integer.parseInt(pageSize);
        if (StringUtils.isBlank(userId)){
            List<OrderModel> all = service.findAll(i,i2);
            if (all!=null&&all.size()>0){
                stringResponseResult.setData(all);
            }
        }else {
            List<OrderModel> allByUserId = service.findAllByUserId(userId, i, i2);
            stringResponseResult.setData(allByUserId);
        }
        return stringResponseResult;
    }

    /**
     * 根据传入状态查询各状态的全部订单
     * @param type 类型：0-已下单未接单 1-未开始已接单  2-已开始未结算  3-已结束未结算 4-已结束已结算  5-已取消
     * @return  json
     */
    @ApiOperation(value = "根据传入状态查询各状态的全部订单")
    @RequestMapping(value = "/findAllByType",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<List<OrderModel>> findAllByType(@ApiParam(value = "当前页数", required = true)
                                              @RequestParam("pageNow") int pageNow,
                              @ApiParam(value = "0-已下单未接单 1-未开始已接单  2-已开始未结算  3-已结束未结算 4-已结束已结算  5-已取消",required = true)
                              @RequestParam("type") String type){
        return service.findAllByType(pageNow, YamlPageUtils.getPageSize(), type);
    }

    /**
     * 根据订单号查询单个订单
     * @param orderSn 订单号
     * @return json
     */
    @ApiOperation(value = "根据订单号查询单个订单")
    @RequestMapping(value = "/getOneByOrderSn",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<OrderModel> getOneByOrderSn(@RequestParam String orderSn){
        ResponseResult<OrderModel> orderModelResponseResult = new ResponseResult<>();
        OrderModel oneByOrderSn = service.getOneByOrderSn(orderSn);
        if (oneByOrderSn!=null){
            orderModelResponseResult.setSuccess(true);
            orderModelResponseResult.setMessage("查询成功");
            orderModelResponseResult.setData(oneByOrderSn);

        }else {
            orderModelResponseResult.setSuccess(false);
            orderModelResponseResult.setMessage("查询失败");
        }
        return orderModelResponseResult;
    }

    /**
     * 根据用户ID查询客户未结算订单或者司机接单未完成订单
     * @param uuid uuid
     * @return json
     */
    @ApiOperation(value = "根据用户ID查询客户未结算订单或者司机接单未完成订单")
    @RequestMapping(value = "/getByUserId",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<List<OrderModel>> getByUserId(@RequestParam("uuid") String uuid){
        ResponseResult<List<OrderModel>> orderModelResponseResult = new ResponseResult<>();
        try {
            ResponseResult<UserModel> responseResult = userService.getById(uuid);
            UserModel user = responseResult.getData();
            //客户，查看其生成的当前未结算订单
            String identity = user.getIdentity();
            String s = "";
            HashMap<String, String> map = new HashMap<>();
            List<OrderModel> byKeHuUserId = service.getByKeHuUserId(uuid);
            if (byKeHuUserId==null||byKeHuUserId.size()==0){
                orderModelResponseResult.setSuccess(false);
                orderModelResponseResult.setMessage("没有待结算单");
            }else {
                orderModelResponseResult.setSuccess(true);
                orderModelResponseResult.setMessage("当前有未结算订单");
                orderModelResponseResult.setData(byKeHuUserId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            orderModelResponseResult.setSuccess(false);
            orderModelResponseResult.setMessage("查询出错");
        }
        return  orderModelResponseResult;
    }

    @ApiOperation("根据订单号更改订单状态")
    @RequestMapping("updateOrderTypeByOrderSn")
    @ResponseBody
    public ResponseResult<String> updateOrderTypeByOrderSn(@RequestParam("orderSn") String orderSn,@RequestParam("type") String type){
        return service.updateOrderTypeByOrderSn(orderSn, type);
    }

    @ApiOperation("乘客以下车")
    @RequestMapping("updateOrderOutCar")
    @ResponseBody
    public ResponseResult<String> updateOrderOutCar(@RequestParam("orderSn")String orderSn,@RequestParam("coordinate")String coordinate){
        return service.updateOrderOutCar(orderSn, coordinate);
    }
}
