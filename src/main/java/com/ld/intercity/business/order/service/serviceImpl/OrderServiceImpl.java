package com.ld.intercity.business.order.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ld.intercity.business.order.dao.OrderMapper;
import com.ld.intercity.business.order.model.OrderModel;
import com.ld.intercity.business.order.service.OrderService;
import com.ld.intercity.business.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    @Transactional
    public int save(OrderModel orderModel
            ,HttpServletRequest request) {
        UserModel user = (UserModel) request.getSession().getAttribute("user");
        orderModel.setCreatePresion(user.getUuid());
        orderModel.setUuid(UUID.randomUUID().toString());
        return orderMapper.save(orderModel);
    }

    @Override
    @Transactional
    public int del(String orderSn) {
        return orderMapper.del(orderSn);
    }

    @Override
    @Transactional
    public int update(OrderModel orderModel) {
        return orderMapper.update(orderModel);
    }

    @Override
    @Transactional
    public int updateJieDan(String orderSn, HttpServletRequest request) {
        UserModel user = (UserModel) request.getSession().getAttribute("user");
        OrderModel oneByOrderSn = getOneByOrderSn(orderSn);
        oneByOrderSn.setJieDanPresion(user.getUuid());
        oneByOrderSn.setJieDanTime(new Date());
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
