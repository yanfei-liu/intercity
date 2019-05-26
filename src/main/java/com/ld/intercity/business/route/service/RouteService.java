package com.ld.intercity.business.route.service;

import com.ld.intercity.business.route.model.RouteModel;

import java.util.List;

public interface RouteService {
    int save (RouteModel routeModel);
    int del(String uuid);
    int update(RouteModel routeModel);
    /**
     * 根据起始地点和目标地点查询路线
     * @param regionOne 起始地点
     * @param regionTwo 目标地点
     * @return
     */
    RouteModel getByRegionOneAndRegionTwo(String regionOne,String regionTwo);
    /**
     * 查询所有delFlag删除标记为0的记录
     * @return
     */
    List<RouteModel> findAll();
}
