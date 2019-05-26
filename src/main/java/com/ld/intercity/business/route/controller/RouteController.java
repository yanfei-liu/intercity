package com.ld.intercity.business.route.controller;

import com.google.gson.Gson;
import com.ld.intercity.business.route.model.RouteModel;
import com.ld.intercity.business.route.service.RouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "路线管理接口",tags = "路线管理接口")
@RequestMapping("/route")
@RestController
@Slf4j
public class RouteController {
    @Autowired
    private RouteService service;

    public String toJson(Object o){
        Gson gson = new Gson();
        String s = gson.toJson(o);
        return s;
    }

    @ApiOperation(value = "保存")
    @RequestMapping("/save")
    @ResponseBody
    public int save(RouteModel routeModel){
        return service.save(routeModel);
    }

    @ApiOperation(value = "删除")
    @RequestMapping("/del")
    @ResponseBody
    public int del(String uuid){
        return service.del(uuid);
    }

    @ApiOperation(value = "修改")
    @RequestMapping("/update")
    @ResponseBody
    public int update(RouteModel routeModel){
        return service.update(routeModel);
    }

    @ApiOperation(value = "根据起始和终点目标查询路线信息")
    @RequestMapping("/getByRegionOneAndRegionTwo")
    @ResponseBody
    public String getByRegionOneAndRegionTwo(@PathVariable("one") String regionOne,@PathVariable("two") String regionTwo){
        RouteModel byRegionOneAndRegionTwo = service.getByRegionOneAndRegionTwo(regionOne, regionTwo);
        String s = toJson(byRegionOneAndRegionTwo);
        return s;
    }

    @ApiOperation(value = "查询全部")
    @RequestMapping("/findAll")
    @ResponseBody
    public String findAll(){
        List<RouteModel> all = service.findAll();
        String s = toJson(all);
        return s;
    }
}
