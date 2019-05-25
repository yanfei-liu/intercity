package com.ld.intercity.business.address.controller;

import com.google.gson.Gson;
import com.ld.intercity.business.address.model.AddressModel;
import com.ld.intercity.business.address.service.AddressService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@ApiOperation("地址管理接口")
@RequestMapping("/address")
@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;

    public String toJson(Object o){
        Gson gson = new Gson();
        String s = gson.toJson(o);
        return s;
    }

    /**
     * 存
     * @param addressModel 实体类
     * @return int
     */
    public String save(AddressModel addressModel){
        HashMap<String, String> map = new HashMap<>();
        int save = addressService.save(addressModel);
        if (save==1){
            map.put("success","true");
        }else {
            map.put("success","false");
        }
        String s = toJson(map);
        return s;
    }

    /**
     * 删——delflag更改为1
     * @param uuid 记录的主键
     * @return int
     */
    public String del(String uuid){
        HashMap<String, String> map = new HashMap<>();
        int del = addressService.del(uuid);
        if (del==1){
            map.put("success","true");
        }else {
            map.put("success","false");
        }
        String s = toJson(map);
        return s;
    }

    /**
     * 改
     * @param addressModel  实体类
     * @return int
     */
    public String update(AddressModel addressModel){
        HashMap<String, String> map = new HashMap<>();
        int upd = addressService.update(addressModel);
        if (upd==1){
            map.put("success","true");
        }else {
            map.put("success","false");
        }
        String s = toJson(map);
        return s;
    }

    /**
     * 根据记录主键查找整条记录
     * @param uuid  记录主键
     * @return AddressModel
     */
    public String getByUuid(String uuid){
        AddressModel byUuid = addressService.getByUuid(uuid);
        String s = toJson(byUuid);
        return s;
    }

    /**
     * 根据记录创建人主键查找全部对应的未删除记录
     * @param uuid 创建人主键
     * @return list
     */
    public String findByCreateBy(String uuid){
        List<AddressModel> byCreateBy = addressService.findByCreateBy(uuid);
        String s = toJson(byCreateBy);
        return s;
    }
}
