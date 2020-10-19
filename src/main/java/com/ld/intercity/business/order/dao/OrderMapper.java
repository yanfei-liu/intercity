package com.ld.intercity.business.order.dao;

import com.github.pagehelper.Page;
import com.ld.intercity.business.order.model.OrderModel;
import org.apache.ibatis.annotations.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrderMapper {
    @Insert("insert into order_table (" +
            "#{o.orderSn},#{o.createPresion},#{o.createName},#{o.phone},#{o.startCoordinate},#{o.endCoordinate},#{o.startProvince},#{o.startCity},#{o.startCounty},#{o.endProvince}," +
            "#{o.endCity},#{o.endCounty},#{o.routeId},#{o.departureTime},#{o.presionNumber},#{o.isCharterCar},#{o.orderAmount},#{o.orderTime},#{o.jieDanPresion},#{o.jieDanTime}," +
            "#{o.orderType},#{o.outCarCoordinate},#{o.outCarTime},#{o.settingTime},#{o.settingPresion}" +
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
            "phone = #{o.phone},start_coordinate = #{o.startCoordinate},end_coordinate = #{o.endCoordinate},start_province = #{o.startProvince},start_city = #{o.startCity},start_county = #{o.startCounty}" +
            "end_province = #{o.endProvince},end_city = #{o.endCity},end_county = #{o.endCounty},routeId = #{o.routeId},departure_time = #{o.departureTime},presion_number = #{o.presionNumber}" +
            "is_charter_car = #{o.isCharterCar},order_amount = #{o.orderAmount},order_time = #{o.orderTime},jie_dan_presion = #{o.jieDanPresion},jie_dan_time = #{o.jieDanTime},order_type = #{o.orderType}" +
            "out_car_coordinate = #{o.outCarCoordinate},out_car_time = #{o.outCarTime},setting_time = #{o.settingTime},setting_presion = #{o.settingPresion} where order_sn = #{orderSn}")
    int update(@Param("o") OrderModel orderModel);

    @Select("select "+
            "order_sn as orderSn,create_presion as createPresion,create_name as createName,phone as phone," +
            "start_province as startProvince,start_city as startCity,start_county as startCounty,end_province as endProvince," +
            "end_city as endCity,end_county as endCounty,departure_time as departureTime,presion_number as presionNumber," +
            "is_charter_car as isCharterCar,order_amount as orderAmount,order_time as orderTime" +
            ",jie_dan_presion as jieDanPresion,jie_dan_time as jieDanTime,order_type as orderType" +
            ",out_car_coordinate as outCarCoordinate,out_car_time as outCarTime,setting_time as settingTime,setting_presion as settingPresion" +
            "from order_table where start_province = #{o.startProvince} and start_city = #{o.startCity} and end_province = #{o.endProvince} and end_city = #{o.endCity}" +
            "and order_time >= #{o.orderTime} and order_type = 0 and del_flag = 0")
    List<OrderModel> findByDriverFindOrder(@Param("o") OrderModel orderModel);

    /**
     * 查询全部订单
     * @return list
     */
    @Select("select " +
            "order_sn as orderSn,create_presion as createPresion,create_name as createName,phone as phone," +
            "start_province as startProvince,start_city as startCity,start_county as startCounty,end_province as endProvince," +
            "end_city as endCity,end_county as endCounty,departure_time as departureTime,presion_number as presionNumber," +
            "is_charter_car as isCharterCar,order_amount as orderAmount,order_type as orderType" +
            "from order_table")
    List<OrderModel> findAll();

    /**
     * 根据传入状态查询各状态的全部订单
     * @param type 类型：0-已下单未接单 1-未开始已接单  2-已开始未结算  3-已结束未结算 4-已结束已结算  5-已取消
     * @return
     */
    @Select("select " +
            "order_sn as orderSn,create_presion as createPresion,create_name as createName,boarding_location = #{o.boardingLocation}," +
            "drop_off_area = #{o.DropOffArea},place_of_departure as placeOfDeparture," +
            "destination as destination,departure_time as departureTime" +
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
            "order_sn as orderSn,create_presion as createPresion,create_name as createName,phone as phone," +
            "start_province as startProvince,start_city as startCity,start_county as startCounty,end_province as endProvince," +
            "end_city as endCity,end_county as endCounty,departure_time as departureTime,presion_number as presionNumber," +
            "is_charter_car as isCharterCar,order_amount as orderAmount,order_time as orderTime" +
            ",jie_dan_presion as jieDanPresion,jie_dan_time as jieDanTime,order_type as orderType" +
            ",out_car_coordinate as outCarCoordinate,out_car_time as outCarTime,setting_time as settingTime,setting_presion as settingPresion" +
            " from order_table where order_sn = #{orderSn}")
    OrderModel getOneByOrderSn(@Param("orderSn") String orderSn);

    /**
     * 根据乘客的用户Id查看未结算的订单
     * @param userId 用户的用户ID
     * @return OrderModel
     */
    @Select("select " +
            "order_sn as orderSn,create_presion as createPresion,create_name as createName,phone as phone," +
            "start_province as startProvince,start_city as startCity,start_county as startCounty,end_province as endProvince," +
            "end_city as endCity,end_county as endCounty,departure_time as departureTime,presion_number as presionNumber," +
            "is_charter_car as isCharterCar,order_amount as orderAmount,order_time as orderTime" +
            ",jie_dan_presion as jieDanPresion,jie_dan_time as jieDanTime,order_type as orderType" +
            ",out_car_coordinate as outCarCoordinate,out_car_time as outCarTime,setting_time as settingTime,setting_presion as settingPresion" +
            " from order_table where create_presion = #{u} and del_flag = 0 and order_type != 5")
    OrderModel getByKeHuUserId(@Param("u") String userId);

    /**
     * 查看司机接单后未完成的订单
     * @param userId 司机的用户ID
     * @return list
     */
    @Select("select " +
            "order_sn as orderSn,create_presion as createPresion,create_name as createName,phone as phone," +
            "start_province as startProvince,start_city as startCity,start_county as startCounty,end_province as endProvince," +
            "end_city as endCity,end_county as endCounty,departure_time as departureTime,presion_number as presionNumber," +
            "is_charter_car as isCharterCar,order_amount as orderAmount,order_time as orderTime" +
            ",jie_dan_presion as jieDanPresion,jie_dan_time as jieDanTime,order_type as orderType" +
            ",out_car_coordinate as outCarCoordinate,out_car_time as outCarTime,setting_time as settingTime,setting_presion as settingPresion" +
            " from order_table where jie_dan_presion = #{u} and del_flag = 0 and order_type != 5")
    List<OrderModel> getBySiJiUserId(@Param("u") String userId);
}
