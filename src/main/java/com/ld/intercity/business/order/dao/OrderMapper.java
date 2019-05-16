package com.ld.intercity.business.order.dao;

import com.github.pagehelper.Page;
import com.ld.intercity.business.order.model.OrderModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("insert into order_table (" +
            "#{o.orderSn},#{o.createPresion},#{o.placeOfDeparture},#{o.destination},#{o.departureTime},#{o.presionNumber},#{o.orderAmount}," +
            "#{o.orderTime},#{o.orderType},#{o.settingTime},#{o.settingPresion}" +
            ")")
    int save(@Param("o") OrderModel orderModel);

    /**
     * 删除订单
     * @param orderSn  订单编号
     * @return int
     */
    @Delete("delete from order_main where order_sn = #{orderSn}")
    int del(@Param("orderSn") String orderSn);

    /**
     * 修改订单
     * @param orderModel 订单实体类
     * @return int
     */
    @Update("update order_table set " +
            "place_of_departure = #{o.placeOfDeparture},destination = #{o.destination},departure_time = #{o.departureTime}" +
            ",presion_number = #{o.presionNumber},order_amount = #{o.orderAmount},order_time = #{o.orderTime}" +
            ",jie_dan_presion = #{o.jieDanPresion},jie_dan_time = #{o.jieDanTime},order_type = #{o.orderType}" +
            ",setting_time = #{o.settingTime},setting_presion = #{o.settingPresion}")
    int update(@Param("o") OrderModel orderModel);

    /**
     * 查询全部订单
     * @return list
     */
    @Select("select " +
            "place_of_departure as placeOfDeparture,destination as destination,departure_time as departureTime" +
            ",presion_number as presionNumber,order_amount as orderAmount,order_time as orderTime" +
            ",order_type as orderType,setting_time as settingTime,setting_presion as settingPresion " +
            "from order_table")
    List<OrderModel> findAll();

    /**
     * 根据传入状态查询各状态的全部订单
     * @param type 类型：0-已下单未接单 1-未开始已接单  2-已开始未结算 3-已结束已结算   4-已取消
     * @return
     */
    @Select("select " +
            "place_of_departure as placeOfDeparture,destination as destination,departure_time as departureTime" +
            ",presion_number as presionNumber,order_amount as orderAmount,order_time as orderTime" +
            ",order_type as orderType,setting_time as settingTime,setting_presion as settingPresion" +
            " from order_table where type = #{type}")
    Page<OrderModel> findAllByType(@Param("type") String type);

    /**
     * 根据订单号查询单个订单
     * @param orderSn 订单号
     * @return OrderModel
     */
    @Select("select " +
            "place_of_departure as placeOfDeparture,destination as destination,departure_time as departureTime" +
            ",presion_number as presionNumber,order_amount as orderAmount,order_time as orderTime" +
            ",order_type as orderType,setting_time as settingTime,setting_presion as settingPresion" +
            " from order_table where order_sn = #{orderSn}")
    OrderModel getOneByOrderSn(@Param("orderSn") String orderSn);
}
