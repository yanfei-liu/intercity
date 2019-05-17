package com.ld.intercity.sys.provinces.controller;


import com.ld.intercity.sys.provinces.model.ProvincesModel;
import com.ld.intercity.sys.provinces.service.ProvincesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "省市县查询接口", tags = "省市县查询操作接口")
@Slf4j
@RestController
@RequestMapping("/provinces")
public class ProvincesController {
    @Autowired
    private ProvincesService service;

    /**
     * 获取省
     * @return list
     */
    @ApiOperation(value = "获取省份")
    @RequestMapping(value = "/findProvince",method = RequestMethod.GET)
    @ResponseBody
    public List<ProvincesModel> findProvince(){
        return service.findProvince();
    }

    /**
     * 获取市
     * @return list
     */
    @ApiOperation(value = "获取市")
    @RequestMapping(value = "/findCity",method = RequestMethod.GET)
    @ResponseBody
    public List<ProvincesModel> findCity(@PathVariable String parentId){
        return service.findCity(parentId);
    }

    /**
     * 获取区/县
     * @return list
     */
    @ApiOperation(value = "获取区/县")
    @RequestMapping(value = "/findCounty",method = RequestMethod.GET)
    @ResponseBody
    public List<ProvincesModel> findCounty(@PathVariable String parentId){
        return service.findCounty(parentId);
    }

    /**
     * 根据区/县Id查找
     * @return ProvincesModel
     */
    @ApiOperation(value = "根据区/县Id查找")
    @RequestMapping(value = "/getByCountyId",method = RequestMethod.GET)
    @ResponseBody
    public ProvincesModel getByCountyId(@PathVariable String id){
        return service.getByCountyId(id);
    }
}
