package com.ld.intercity.business.route.service.serviceImpl;

import com.ld.intercity.business.route.Mapper.RouteMapper;
import com.ld.intercity.business.route.model.RouteModel;
import com.ld.intercity.business.route.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteMapper mapper;

    @Override
    public int save(RouteModel routeModel) {
        routeModel.setUuid(UUID.randomUUID().toString());
        return mapper.save(routeModel);
    }

    @Override
    public int del(String uuid) {
        return mapper.del(uuid);
    }

    @Override
    public int update(RouteModel routeModel) {
        return mapper.update(routeModel);
    }

    @Override
    public RouteModel getByRegionOneAndRegionTwo(String provinceOne, String cityOne, String countyOne, String provinceTwo, String cityTwo, String countyTwo) {
        return mapper.getByRegionOneAndRegionTwo(provinceOne, cityOne, countyOne, provinceTwo, cityTwo, countyTwo);
    }


    @Override
    public List<RouteModel> findAll() {
        return mapper.findAll();
    }

    @Override
    public RouteModel getById(String uuid) {
        return mapper.getById(uuid);
    }


}
