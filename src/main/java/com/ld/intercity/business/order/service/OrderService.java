package com.ld.intercity.business.order.service;

import com.github.pagehelper.PageInfo;
import com.ld.intercity.business.order.model.OrderModel;
import com.ld.intercity.utils.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderService {
    /**
     * 生成订单
     * @param orderModel 订单实体类
     * @return map
     */
    Map save(OrderModel orderModel);

    /**
     * 删除订单
     * @param orderSn  订单编号
     * @return map
     */
    Map del(String orderSn);

    /**
     * 修改订单
     * @param orderModel 订单实体类
     * @return int
     */
    int update(OrderModel orderModel);

    /**
     * 司机去接单大厅检索订单
     * 根据司机输入的出发城市/目的地城市以及时间（未指定默认当前时间）等条件检索已下单未结单的订单
     * @param routeId    线路ID
     * @param startDate    开始时间
     * @param endDate    结束时间
     * @param pageNo     起始下标
     * @param pageSize   每页条数
     * @return  map
     */
    ResponseResult<List<OrderModel>> findByDriverFindOrder(String routeId,String startDate,String endDate, int pageNo, int pageSize);

    /**
     * 接单
     * @param orderSn 订单编号
     * @return int
     */
    int updateJieDan(String orderSn, String uuid);

    /**
     * 接单记录
     * @param uuid
     * @return
     */
    ResponseResult<List<OrderModel>> findOrderByDriver(String uuid);

    /**
     * 查询全部订单
     * @return list
     */
    List<OrderModel> findAll(int i,int i2);

    List<OrderModel> findAllByUserId(String userId,int i,int i2);

    /**
     * 根据传入状态查询各状态的全部订单
     * @param type 类型：0-已下单未接单 1-未开始已接单  2-已开始未结算 3-已结束已结算   4-已取消
     * @return
     */
    ResponseResult<List<OrderModel>> findAllByType(int pageNow, int pageSize, String type);

    /**
     * 根据订单号查询单个订单
     * @param orderSn 订单号
     * @return OrderModel
     */
    OrderModel getOneByOrderSn(String orderSn);

    /**
     * 根据乘客的用户Id查看未结算的订单
     * @param userId 用户的用户ID
     * @return OrderModel
     */
    List<OrderModel> getByKeHuUserId(String userId);

    /**
     * 查看司机接单后未完成的订单
     * @param userId 司机的用户ID
     * @return list
     */
    List<OrderModel> getBySiJiUserId(String userId);

    /**
     * 订单结算
     * @param orderSn   订单编号
     * @return  ResponseResult
     */
    ResponseResult<String> orderSetting(String orderSn);

    /**
     * 根据订单号更改订单状态
     * @param orderSn    订单编号
     * @param type  订单状态
     * @return  ResponseResult
     */
    ResponseResult<String> updateOrderTypeByOrderSn(String orderSn, String type);

    /**
     * 乘客已下车
     * @param orderSn   订单编号
     * @param coordinate    下车地点坐标
     * @return  int
     */
    ResponseResult<String> updateOrderOutCar(String orderSn,String coordinate);
}
