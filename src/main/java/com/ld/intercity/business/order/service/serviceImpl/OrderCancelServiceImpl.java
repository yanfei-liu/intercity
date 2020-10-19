package com.ld.intercity.business.order.service.serviceImpl;

import com.ld.intercity.business.order.dao.OrderCancelMapper;
import com.ld.intercity.business.order.model.OrderCancelModel;
import com.ld.intercity.business.order.service.OrderCancelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class OrderCancelServiceImpl implements OrderCancelService {
    @Autowired
    private OrderCancelMapper orderCancelMapper;

    @Override
    @Transactional
    public int save(OrderCancelModel orderCancelModel) {
        return orderCancelMapper.save(orderCancelModel);
    }

    @Override
    public OrderCancelModel getByUserId(String userId) {
        return orderCancelMapper.getByUserId(userId);
    }

    @Override
    public int deleteByUuid(String uuid) {
        return orderCancelMapper.deleteByUuid(uuid);
    }
}
