package com.ld.intercity.business.order.service;


import com.ld.intercity.business.order.model.OrderCancelModel;

public interface OrderCancelService {
    /**
     * 添加订单取消记录
     * @param orderCancelModel 待添加实体类
     * @return int
     * @author cxc
     */
    int save(OrderCancelModel orderCancelModel);

    /**
     * 根据用户ID查询未作废的订单取消记录
     * @param userId    用户ID
     * @return  OrderCancelModel
     * @author  cxc
     */
    OrderCancelModel getByUserId(String userId);

    /**
     * 删除指定的订单作废记录
     * @param uuid  指定记录的uuid
     * @return  int
     * @author cxc
     */
    int deleteByUuid(String uuid);
}
