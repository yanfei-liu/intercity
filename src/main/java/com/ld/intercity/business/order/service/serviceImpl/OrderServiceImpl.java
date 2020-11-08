package com.ld.intercity.business.order.service.serviceImpl;

import com.ld.intercity.business.order.dao.OrderMapper;
import com.ld.intercity.business.order.model.OrderCancelModel;
import com.ld.intercity.business.order.model.OrderModel;
import com.ld.intercity.business.order.service.OrderCancelService;
import com.ld.intercity.business.order.service.OrderService;
import com.ld.intercity.business.route.model.RouteModel;
import com.ld.intercity.business.route.service.RouteService;
import com.ld.intercity.utils.ResponseResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderCancelService orderCancelService;
    @Autowired
    private RouteService routeService;
    @Override
    @Transactional
    public Map save(OrderModel orderModel) {
        HashMap<String, String> map = new HashMap<>();
        String createPresion = orderModel.getCreatePresion();
        OrderCancelModel byUserId = orderCancelService.getByUserId(createPresion);
        List<OrderModel> byKeHuUserId = orderMapper.getByKeHuUserId(createPresion);
        if (byKeHuUserId!=null&&byKeHuUserId.size()>0){
            map.put("code","1003");
            map.put("msg","当前用户存在未结算订单");
        }else {
            int i = 0;
            //  判断是否存在一分钟内取消的订单
            if (byUserId != null){
                LocalDateTime cancelTime = byUserId.getCancelTime();
                LocalDateTime now = LocalDateTime.now();
                Duration between = Duration.between(now, cancelTime);
                if (between.toMinutes()<=1){
                    map.put("code","1001");
                    map.put("msg","请等待1分钟后再下单");
                }else {
                    orderCancelService.deleteByUuid(byUserId.getUuid());
                    i++;
                }
            }else {
                i++;
            }
            if (i>0){
                orderModel.setUuid(UUID.randomUUID().toString());
                orderModel.setOrderSn(Long.toString(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"))));
                if (StringUtils.isBlank(orderModel.getCreateName())){
                    String phone = orderModel.getPhone();
                    String s = new String();
                    if (phone.length()>4){
                        s = phone.substring(phone.length() - 4, phone.length());
                    }else {
                        s = phone;
                    }
                    orderModel.setCreateName("尾号：" + s);
                }
                orderModel.setOrderType("0");
                //  根据出发省市县以及目的地省市县查询相对应的路线
                RouteModel byRegionOneAndRegionTwo = routeService.getById(orderModel.getRouteId());
                orderModel.setStartProvince(byRegionOneAndRegionTwo.getProvinceOne());
                orderModel.setStartCity(byRegionOneAndRegionTwo.getCityOne());
                orderModel.setStartCounty(byRegionOneAndRegionTwo.getCountyOne());
                orderModel.setEndProvince(byRegionOneAndRegionTwo.getProvinceTwo());
                orderModel.setEndCity(byRegionOneAndRegionTwo.getCityTwo());
                orderModel.setEndCounty(byRegionOneAndRegionTwo.getCountyTwo());
                String presionNumber = orderModel.getPresionNumber();
                //  订单金额计算
                if ("0".equals(orderModel.getIsCharterCar())){  //不包车
                    BigDecimal multiply = new BigDecimal(presionNumber).multiply(byRegionOneAndRegionTwo.getOneUserPrice());
                    orderModel.setOrderAmount(multiply.toString());
                }else {     //  包车
                    if ("1".equals(orderModel.getCharterCarType())){    //  五座
                        orderModel.setOrderAmount(byRegionOneAndRegionTwo.getFiveSeatsPrice().toString());
                    }else {     //  七座
                        orderModel.setOrderAmount(byRegionOneAndRegionTwo.getSevenSeatsPrice().toString());
                    }
                }
                orderModel.setOrderTime(new Date());
                orderModel.setDelFlag("0");
                int save = orderMapper.save(orderModel);
                if (save!=1){
                    map.put("code","1002");
                    map.put("msg","订单生产失败，请稍后再试");
                }else {
                    map.put("code","0");
                    map.put("msg","订单已生成，请等待司机接单");
                }
            }
        }
        return map;
    }

    @Override
    @Transactional
    public Map del(String orderSn) {
        Map<String, String> map = new HashMap<>();
        //  查询被取消的订单
        OrderModel oneByOrderSn = getOneByOrderSn(orderSn);
        //  下单一分钟内不得取消
        Date orderTime = oneByOrderSn.getOrderTime();
        Instant instant = orderTime.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        Duration between = Duration.between(LocalDateTime.now(), localDateTime);
        if (between.toMinutes()<=1){    //  取消时间与下单时间小于一分钟
            map.put("code","1001");
            map.put("msg","下单后一分钟内不得取消订单");
        }else if ("2".equals(oneByOrderSn.getOrderType())||"3".equals(oneByOrderSn.getOrderType())||"4".equals(oneByOrderSn.getOrderType())){
            map.put("code","1002");
            map.put("msg","当前订单状态不可取消，请联系人工客服");
        }else if ("5".equals(oneByOrderSn.getOrderType())){
            map.put("code","1003");
            map.put("msg","该订单已取消");
        } else {
            //  添加取消记录
            OrderCancelModel orderCancelModel = new OrderCancelModel();
            orderCancelModel.setUuid(UUID.randomUUID().toString());
            orderCancelModel.setOrderSn(orderSn);
            orderCancelModel.setCancelUserId(oneByOrderSn.getCreatePresion());
            orderCancelModel.setCancelTime(LocalDateTime.now());
            orderCancelModel.setDelFlag("0");
            orderCancelService.save(orderCancelModel);
            orderMapper.del(orderSn);
            map.put("code","0");
            map.put("msg","订单已成功取消，请于1分钟后再次下单");
        }
        return map;
    }

    @Override
    @Transactional
    public int update(OrderModel orderModel) {
        return orderMapper.update(orderModel);
    }

    @Override
    public ResponseResult<List<OrderModel>> findByDriverFindOrder(String routeId,String startDate,String endDate, int pageNo, int pageSize) {
        ResponseResult<List<OrderModel>> listResponseResult = new ResponseResult<>();
        List<OrderModel> byDriverFindOrder = orderMapper.findByDriverFindOrder(routeId, startDate, endDate, pageNo, pageSize);
        if (byDriverFindOrder==null||byDriverFindOrder.size()==0){
            listResponseResult.setSuccess(false);
            listResponseResult.setMessage("当前暂无符合条件的订单");
        }else {
            listResponseResult.setSuccess(true);
            listResponseResult.setMessage("查询成功");
            listResponseResult.setData(byDriverFindOrder);
        }
        return listResponseResult;
    }

    @Override
    @Transactional
    public int updateJieDan(String orderSn, String uuid) {
        OrderModel oneByOrderSn = getOneByOrderSn(orderSn);
        oneByOrderSn.setJieDanPresion(uuid);
        oneByOrderSn.setJieDanTime(new Date());
        oneByOrderSn.setOrderType("1");
        return update(oneByOrderSn);
    }

    @Override
    public ResponseResult<List<OrderModel>> findOrderByDriver(String uuid) {
        ResponseResult<List<OrderModel>> listResponseResult = new ResponseResult<>();
        List<OrderModel> orderByDriver = orderMapper.findOrderByDriver(uuid);
        if (orderByDriver!=null&&orderByDriver.size()>0){
            listResponseResult.setSuccess(true);
            listResponseResult.setMessage("查询成功");
            listResponseResult.setData(orderByDriver);
        }else {
            listResponseResult.setSuccess(true);
            listResponseResult.setMessage("当前未查询到记录");
        }
        return listResponseResult;
    }

    @Override
    public List<OrderModel> findAll(int i,int i2) {
        return orderMapper.findAll(i,i2);
    }

    @Override
    public List<OrderModel> findAllByUserId(String userId, int i, int i2) {
        return orderMapper.findAllByUserId(userId, i, i2);
    }

    @Override
    public ResponseResult<List<OrderModel>> findAllByType(int pageNow,int pageSize,String type) {
        ResponseResult<List<OrderModel>> orderModelResponseResult = new ResponseResult<>();
        List<OrderModel> allByType = orderMapper.findAllByType(type,pageNow,pageSize);
        orderModelResponseResult.setData(allByType);
        return orderModelResponseResult;
    }

    @Override
    public OrderModel getOneByOrderSn(String orderSn) {
        return orderMapper.getOneByOrderSn(orderSn);
    }

    @Override
    public List<OrderModel> getByKeHuUserId(String userId) {
        return orderMapper.getByKeHuUserId(userId);
    }

    @Override
    public List<OrderModel> getBySiJiUserId(String userId) {
        return orderMapper.getBySiJiUserId(userId);
    }

    @Override
    @Transactional
    public ResponseResult<String> orderSetting(String orderSn) {
        int i = orderMapper.orderSetting(orderSn);
        ResponseResult<String> stringResponseResult = new ResponseResult<>();
        if (i==1){
            stringResponseResult.setSuccess(true);
            stringResponseResult.setMessage("结算成功");
        }else {
            stringResponseResult.setSuccess(false);
            stringResponseResult.setMessage("结算失败");
        }
        return stringResponseResult;
    }

    @Override
    @Transactional
    public ResponseResult<String> updateOrderTypeByOrderSn(String orderSn, String type) {
        ResponseResult<String> stringResponseResult = new ResponseResult<>();
        int i = orderMapper.updateOrderTypeByOrderSn(orderSn, type);
        if (i==1){
            stringResponseResult.setSuccess(true);
            stringResponseResult.setMessage("状态更改成功");
        }else {
            stringResponseResult.setSuccess(false);
            stringResponseResult.setMessage("状态更改失败");
        }
        return stringResponseResult;
    }

    @Override
    @Transactional
    public ResponseResult<String> updateOrderOutCar(String orderSn, String coordinate) {
        ResponseResult<String> stringResponseResult = new ResponseResult<>();
        OrderModel oneByOrderSn = getOneByOrderSn(orderSn);
        oneByOrderSn.setOutCarCoordinate(coordinate);
        oneByOrderSn.setOutCarTime(new Date());
        oneByOrderSn.setOrderType("3");
        int i = orderMapper.updateOrderOutCar(oneByOrderSn);
        if (i==1){
            stringResponseResult.setSuccess(true);
            stringResponseResult.setMessage("乘客下车已记录,可以进行结算");
        }else {
            stringResponseResult.setSuccess(false);
            stringResponseResult.setMessage("乘客下车记录失败,请再次尝试");
        }
        return stringResponseResult;
    }
}
