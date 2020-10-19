package com.ld.intercity.business.order.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ld.intercity.business.order.dao.OrderMapper;
import com.ld.intercity.business.order.model.OrderCancelModel;
import com.ld.intercity.business.order.model.OrderModel;
import com.ld.intercity.business.order.service.OrderCancelService;
import com.ld.intercity.business.order.service.OrderService;
import com.ld.intercity.business.route.model.RouteModel;
import com.ld.intercity.business.route.service.RouteService;
import com.ld.intercity.business.user.model.UserModel;
import org.omg.CORBA.ORB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    public Map save(OrderModel orderModel
            , HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<>();
        UserModel user = (UserModel) request.getSession().getAttribute("user");
        OrderCancelModel byUserId = orderCancelService.getByUserId(user.getUuid());
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
            orderModel.setCreatePresion(user.getUuid());
            orderModel.setUuid(UUID.randomUUID().toString());
            orderModel.setOrderSn(Long.toString(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"))));
            orderModel.setOrderType("0");
            //  根据出发省市县以及目的地省市县查询相对应的路线
            RouteModel byRegionOneAndRegionTwo = routeService.getByRegionOneAndRegionTwo(
                    orderModel.getStartProvince(), orderModel.getStartCity(), orderModel.getStartCounty(),
                    orderModel.getEndProvince(), orderModel.getEndCity(), orderModel.getEndCounty());
            orderModel.setRouteId(byRegionOneAndRegionTwo.getUuid());
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
            orderModel.setOrderTime(LocalDateTime.now());
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
        return map;
    }

    @Override
    @Transactional
    public Map del(String orderSn) {
        Map<String, String> map = new HashMap<>();
        //  查询被取消的订单
        OrderModel oneByOrderSn = getOneByOrderSn(orderSn);
        //  下单一分钟内不得取消
        Duration between = Duration.between(LocalDateTime.now(), oneByOrderSn.getOrderTime());
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
    public List<OrderModel> findByDriverFindOrder(OrderModel orderModel) {
        if (orderModel.getOrderTime()==null){
            orderModel.setOrderTime(LocalDateTime.now());
        }
        return orderMapper.findByDriverFindOrder(orderModel);
    }

    @Override
    @Transactional
    public int updateJieDan(String orderSn, HttpServletRequest request) {
        UserModel user = (UserModel) request.getSession().getAttribute("user");
        OrderModel oneByOrderSn = getOneByOrderSn(orderSn);
        oneByOrderSn.setJieDanPresion(user.getUuid());
        oneByOrderSn.setJieDanTime(LocalDateTime.now());
        return update(oneByOrderSn);
    }

    @Override
    public List<OrderModel> findAll() {
        return orderMapper.findAll();
    }

    @Override
    public PageInfo<OrderModel> findAllByType(int pageNow,int pageSize,String type) {
        PageHelper.startPage(pageNow,pageSize);
        Page<OrderModel> allByType = orderMapper.findAllByType(type);
        if (allByType!=null){
            PageInfo<OrderModel> orderModelPageInfo = new PageInfo<>(allByType);
            return orderModelPageInfo;
        }else {
            return null;
        }
    }

    @Override
    public OrderModel getOneByOrderSn(String orderSn) {
        return orderMapper.getOneByOrderSn(orderSn);
    }

    @Override
    public OrderModel getByKeHuUserId(String userId) {
        return orderMapper.getByKeHuUserId(userId);
    }

    @Override
    public List<OrderModel> getBySiJiUserId(String userId) {
        return orderMapper.getBySiJiUserId(userId);
    }
}
