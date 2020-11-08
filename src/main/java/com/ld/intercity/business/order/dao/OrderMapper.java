package com.ld.intercity.business.order.dao;

import com.github.pagehelper.Page;
import com.ld.intercity.business.order.model.OrderModel;
import com.ld.intercity.utils.ResponseResult;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface OrderMapper {
    @Insert("insert into order_table values (#{o.uuid}," +
            "#{o.orderSn},#{o.createPresion},#{o.createName},#{o.phone},#{o.startCoordinate},#{o.startAddress},#{o.endCoordinate},#{o.endAddress},#{o.startProvince},#{o.startCity},#{o.startCounty},#{o.endProvince}," +
            "#{o.endCity},#{o.endCounty},#{o.routeId},#{o.departureTime},#{o.presionNumber},#{o.isCharterCar},#{o.charterCarType},#{o.orderAmount},#{o.orderTime},#{o.jieDanPresion},#{o.jieDanTime}," +
            "#{o.orderType},#{o.outCarCoordinate},#{o.outCarTime},#{o.settingTime},#{o.settingPresion},#{o.delFlag})")
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
            "phone = #{o.phone},start_coordinate = #{o.startCoordinate},start_address = #{o.startAddress},end_coordinate = #{o.endCoordinate},end_address = #{o.endAddress}," +
            "start_province = #{o.startProvince},start_city = #{o.startCity},start_county = #{o.startCounty},end_province = #{o.endProvince},end_city = #{o.endCity}," +
            "end_county = #{o.endCounty},route_id = #{o.routeId},departure_time = #{o.departureTime},presion_number = #{o.presionNumber},is_charter_car = #{o.isCharterCar}," +
            "order_amount = #{o.orderAmount},order_time = #{o.orderTime},jie_dan_presion = #{o.jieDanPresion},jie_dan_time = #{o.jieDanTime},order_type = #{o.orderType}," +
            "out_car_coordinate = #{o.outCarCoordinate},out_car_time = #{o.outCarTime},setting_time = #{o.settingTime},setting_presion = #{o.settingPresion} where order_sn = #{o.orderSn}")
    int update(@Param("o") OrderModel orderModel);

    @Select("select "+
            "order_sn as orderSn,create_presion as createPresion,create_name as createName,phone as phone," +
            "start_coordinate as startCoordinate,start_address as startAddress,end_coordinate as endCoordinate,end_address as endAddress," +
            "start_province as startProvince,start_city as startCity,start_county as startCounty,end_province as endProvince," +
            "end_city as endCity,end_county as endCounty,departure_time as departureTime,presion_number as presionNumber," +
            "is_charter_car as isCharterCar,order_amount as orderAmount,order_time as orderTime" +
            ",jie_dan_presion as jieDanPresion,jie_dan_time as jieDanTime,order_type as orderType" +
            ",out_car_coordinate as outCarCoordinate,out_car_time as outCarTime,setting_time as settingTime,setting_presion as settingPresion " +
            "from order_table where route_id = #{routeId} and departure_time >= #{startDate} and departure_time <= #{endDate} and order_type = 0 " +
            "and del_flag = 0 limit ${pageNo},${pageSize}")
    List<OrderModel> findByDriverFindOrder(@Param("routeId") String routeId,@Param("startDate") String startDate, @Param("endDate") String endDate,
                                           @Param("pageNo") int pageNo,@Param("pageSize") int pageSize);

    /**
     * 查询全部订单
     * @return list
     */
    @Select("select " +
            "order_sn as orderSn,create_presion as createPresion,create_name as createName,phone as phone," +
            "start_coordinate as startCoordinate,start_address as startAddress,end_coordinate as endCoordinate,end_address as endAddress," +
            "start_province as startProvince,start_city as startCity,start_county as startCounty,end_province as endProvince," +
            "end_city as endCity,end_county as endCounty,departure_time as departureTime,presion_number as presionNumber," +
            "is_charter_car as isCharterCar,order_amount as orderAmount,order_type as orderType " +
            "from order_table limit ${i},${i2}")
    List<OrderModel> findAll(@Param("i") int i,@Param("i2") int i2);

    /**
     * 查询全部订单
     * @return list
     */
    @Select("select " +
            "order_sn as orderSn,create_presion as createPresion,create_name as createName,phone as phone," +
            "start_coordinate as startCoordinate,start_address as startAddress,end_coordinate as endCoordinate,end_address as endAddress," +
            "start_province as startProvince,start_city as startCity,start_county as startCounty,end_province as endProvince," +
            "end_city as endCity,end_county as endCounty,departure_time as departureTime,presion_number as presionNumber," +
            "is_charter_car as isCharterCar,order_amount as orderAmount,order_type as orderType " +
            "from order_table where create_presion = #{userId} limit ${i},${i2}")
    List<OrderModel> findAllByUserId(@Param("userId") String userId,@Param("i") int i,@Param("i2") int i2);

    /**
     * 根据传入状态查询各状态的全部订单
     * @param type 类型：0-已下单未接单 1-未开始已接单  2-已开始未结算  3-已结束未结算 4-已结束已结算  5-已取消
     * @return
     */
    @Select("select " +
            "order_sn as orderSn,create_presion as createPresion,create_name as createName,phone as phone," +
            "start_coordinate as startCoordinate,start_address as startAddress,end_coordinate as endCoordinate,end_address as endAddress," +
            "start_province as startProvince,start_city as startCity,start_county as startCounty,end_province as endProvince," +
            "end_city as endCity,end_county as endCounty,departure_time as departureTime,presion_number as presionNumber," +
            "is_charter_car as isCharterCar,order_amount as orderAmount,order_type as orderType" +
            " from order_table where type = #{type} limit ${pageNo},${pageSize}")
    Page<OrderModel> findAllByType(@Param("type") String type,@Param("pageNo")int pageNo,@Param("pageSize")int pageSize);

    /**
     * 根据订单号查询单个订单
     * @param orderSn 订单号
     * @return OrderModel
     */
    @Select("select " +
            "order_sn as orderSn,create_presion as createPresion,create_name as createName,phone as phone," +
            "start_coordinate as startCoordinate,start_address as startAddress,end_coordinate as endCoordinate,end_address as endAddress," +
            "start_province as startProvince,start_city as startCity,start_county as startCounty,end_province as endProvince," +
            "end_city as endCity,end_county as endCounty,route_id as routeId,departure_time as departureTime,presion_number as presionNumber," +
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
            "start_coordinate as startCoordinate,start_address as startAddress,end_coordinate as endCoordinate,end_address as endAddress," +
            "start_province as startProvince,start_city as startCity,start_county as startCounty,end_province as endProvince," +
            "end_city as endCity,end_county as endCounty,departure_time as departureTime,presion_number as presionNumber," +
            "is_charter_car as isCharterCar,order_amount as orderAmount,order_time as orderTime" +
            ",jie_dan_presion as jieDanPresion,jie_dan_time as jieDanTime,order_type as orderType" +
            ",out_car_coordinate as outCarCoordinate,out_car_time as outCarTime,setting_time as settingTime,setting_presion as settingPresion" +
            " from order_table where create_presion = #{u} and del_flag = 0 and order_type != 5 and order_type != 4")
    List<OrderModel> getByKeHuUserId(@Param("u") String userId);

    /**
     * 查看司机接单后未完成的订单
     * @param userId 司机的用户ID
     * @return list
     */
    @Select("select " +
            "order_sn as orderSn,create_presion as createPresion,create_name as createName,phone as phone," +
            "start_coordinate as startCoordinate,start_address as startAddress,end_coordinate as endCoordinate,end_address as endAddress," +
            "start_province as startProvince,start_city as startCity,start_county as startCounty,end_province as endProvince," +
            "end_city as endCity,end_county as endCounty,departure_time as departureTime,presion_number as presionNumber," +
            "is_charter_car as isCharterCar,order_amount as orderAmount,order_time as orderTime" +
            ",jie_dan_presion as jieDanPresion,jie_dan_time as jieDanTime,order_type as orderType" +
            ",out_car_coordinate as outCarCoordinate,out_car_time as outCarTime,setting_time as settingTime,setting_presion as settingPresion" +
            " from order_table where jie_dan_presion = #{u} and del_flag = 0 and order_type != 5 and order_type != 4 ORDER BY departure_time")
    List<OrderModel> getBySiJiUserId(@Param("u") String userId);

    @Select("select " +
            "order_sn as orderSn,create_presion as createPresion,create_name as createName,phone as phone," +
            "start_coordinate as startCoordinate,start_address as startAddress,end_coordinate as endCoordinate,end_address as endAddress," +
            "start_province as startProvince,start_city as startCity,start_county as startCounty,end_province as endProvince," +
            "end_city as endCity,end_county as endCounty,departure_time as departureTime,presion_number as presionNumber," +
            "is_charter_car as isCharterCar,order_amount as orderAmount,order_time as orderTime" +
            ",jie_dan_presion as jieDanPresion,jie_dan_time as jieDanTime,order_type as orderType" +
            ",out_car_coordinate as outCarCoordinate,out_car_time as outCarTime,setting_time as settingTime,setting_presion as settingPresion" +
            " from order_table where jie_dan_presion = #{uuid} and del_flag = 0 ORDER BY departure_time , order_type")
    List<OrderModel> findOrderByDriver(@Param("uuid") String uuid);

    /**
     * 订单结算
     * @param orderSn   订单编号
     * @return  int
     */
    @Update("update order_table set order_type = '4' , setting_time = now() where order_sn = #{orderSn} and del_flag = 0")
    int orderSetting(@Param("orderSn") String orderSn);


    /**
     * 根据订单号更改订单状态
     * @param orderSn    订单编号
     * @param type  订单状态
     * @return  int
     */
    @Update("update order_table set order_type = #{type} where order_sn = #{orderSn} and  del_flag = 0")
    int updateOrderTypeByOrderSn(@Param("orderSn") String orderSn,@Param("type") String type);

    /**
     * 乘客已下车
     * @param orderModel   订单
     * @return  int
     */
    @Update("update order_table set order_type = #{o.orderType},out_car_coordinate = #{o.outCarCoordinate}," +
            "out_car_time = #{o.outCarTime} where order_sn = #{o.orderSn} and  del_flag = 0")
    int updateOrderOutCar(@Param("o")OrderModel orderModel);
}
