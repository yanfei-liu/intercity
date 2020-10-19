package com.ld.intercity.business.route.service;

import com.ld.intercity.business.route.model.RouteModel;

import java.util.List;

public interface RouteService {
    int save (RouteModel routeModel);
    int del(String uuid);
    int update(RouteModel routeModel);
    /**
     * 根据起始地点和目标地点查询路线
     * @param provinceOne 起始地省
     * @param cityOne 目标地市
     * @param countyOne 目标地区县
     * @param provinceTwo 目标地省
     * @param cityTwo 目标地市
     * @param countyTwo 目标地区县
     * @return  RouteModel
     */
    RouteModel getByRegionOneAndRegionTwo(
            String provinceOne,String cityOne,String countyOne,
            String provinceTwo,String cityTwo,String countyTwo);
    /**
     * 查询所有delFlag删除标记为0的记录
     * @return
     */
    List<RouteModel> findAll();
}
