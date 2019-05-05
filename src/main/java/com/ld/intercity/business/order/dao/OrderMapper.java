package com.ld.intercity.business.order.dao;

import com.ld.intercity.business.order.model.OrderModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

    int save(@Param("o") OrderModel orderModel);
}
