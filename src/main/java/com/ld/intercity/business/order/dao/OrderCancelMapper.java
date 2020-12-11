package com.ld.intercity.business.order.dao;

import com.ld.intercity.business.order.model.OrderCancelModel;
import feign.Param;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OrderCancelMapper {
    @Insert("insert into order_cancel_table (uuid,order_sn,cancel_user_id,cancel_time,del_flag) values" +
            " (#{uuid},#{orderSn},#{cancelUserId},#{cancelTime},#{delFlag})")
    int save(OrderCancelModel orderCancelModel);

    @Select("select uuid,order_sn as orderSn,cancel_user_id as cancelUserId,cancel_time as cancelTime,del_flag as delFlag from order_cancel_table " +
            "where cancel_user_id = #{userId} and del_flag = '0'")
    OrderCancelModel getByUserId(@Param("userId") String userId);

    @Update("update order_cancel_table set del_flag = '1' where uuid = #{uuid}")
    int deleteByUuid(@Param("uuid") String uuid);
}
