package com.ld.intercity.business.order.dao.sql;

import com.ld.intercity.business.order.model.OrderModel;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class OrderMapperSql {
    private String sql = "order_sn as orderSn," +
            "create_presion as createPresion," +
            "create_name as createName," +
            "phone as phone," +
            "start_coordinate as startCoordinate," +
            "start_address as startAddress," +
            "end_coordinate as endCoordinate," +
            "end_address as endAddress," +
            "start_province as startProvince," +
            "start_city as startCity," +
            "start_county as startCounty," +
            "end_province as endProvince," +
            "end_city as endCity," +
            "end_county as endCounty," +
            "departure_time as departureTime," +
            "presion_number as presionNumber," +
            "is_charter_car as isCharterCar," +
            "order_amount as orderAmount," +
            "order_time as orderTime" +
            ",jie_dan_presion as jieDanPresion," +
            "jie_dan_time as jieDanTime," +
            "order_type as orderType" +
            ",out_car_coordinate as outCarCoordinate," +
            "out_car_time as outCarTime," +
            "setting_time as settingTime," +
            "setting_presion as settingPresion";

    public String findOrderByQuery(@Param("orderModel")OrderModel orderModel){
        return new SQL(){
            {
                SELECT("order_sn as orderSn,create_name as createName,start_address as startAddress," +
                        "end_address as endAddress,start_province as startProvince,start_city as startCity," +
                        "start_county as startCounty,end_province as endProvince,end_city as endCity," +
                        "end_county as endCounty,departure_time as departureTime,presion_number as presionNumber,"+
                        "order_type as orderType");
                FROM("order_table");
                String sql = "del_flag = 0 ";
                if (StringUtils.isNotBlank(orderModel.getOrderSn())) {
                    sql += "and order_sn=#{orderModel.orderSn}";
                }
                if (StringUtils.isNotBlank(orderModel.getOrderType())){
                    sql += "and order_type = #{orderModel.orderType}";
                }
                if (StringUtils.isNotBlank(orderModel.getRouteId())){
                    sql += "and route_id = #{orderModel.routeId} ";
                }
                WHERE(sql);
            }
        }.toString();
    }
}
