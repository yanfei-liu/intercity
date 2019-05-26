package com.ld.intercity.business.route.controller;

import com.google.gson.Gson;
import com.ld.intercity.business.route.model.RouteModel;
import com.ld.intercity.business.route.service.RouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public int save(RouteModel routeModel){
        return service.save(routeModel);
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/del",method = RequestMethod.GET)
    @ResponseBody
    public int del(String uuid){
        return service.del(uuid);
    }

    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public int update(RouteModel routeModel){
        return service.update(routeModel);
    }

    @ApiOperation(value = "根据起始和终点目标查询路线信息")
    @RequestMapping(value = "/getByRegionOneAndRegionTwo",method = RequestMethod.GET)
    @ResponseBody
    public String getByRegionOneAndRegionTwo(@PathVariable("one") String regionOne,@PathVariable("two") String regionTwo){
        RouteModel byRegionOneAndRegionTwo = service.getByRegionOneAndRegionTwo(regionOne, regionTwo);
        String s = toJson(byRegionOneAndRegionTwo);
        return s;
    }

    @ApiOperation(value = "查询全部")
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ResponseBody
    public String findAll(){
        List<RouteModel> all = service.findAll();
        String s = toJson(all);
        return s;
    }
}
