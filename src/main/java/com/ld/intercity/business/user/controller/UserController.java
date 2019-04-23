package com.ld.intercity.business.user.controller;

import com.github.pagehelper.PageInfo;
import com.ld.intercity.business.user.model.UserModel;
import com.ld.intercity.business.user.service.UserService;
import com.ld.intercity.utils.ResponseResult;
import com.ld.intercity.utils.yaml.YamlPageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Explain
 * @Author Dong-Liu
 * @Date 2019/4/20 20:16
 **/
@Api(value = "用户接口", tags = "用户基本资料操作接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @ApiOperation(value = "查询用户", notes = "根据用户id查询用户资料")
    @RequestMapping(value = "/user/{uuid}", method = RequestMethod.GET)
    public ResponseResult<UserModel> getById(@ApiParam(value = "数据id", required = true)
                                             @PathVariable("uuid") String uuid) throws Exception {
        return service.getById(uuid);
    }

    @ApiOperation(value = "修改用户", notes = "根据id修改用户资料")
    @RequestMapping(value = "/user/{uuid}", method = RequestMethod.PUT)
    public ResponseResult<UserModel> updateById(@ApiParam(value = "数据id", required = true)
                                                @PathVariable("uuid") String uuid,
                                                @ApiParam(value = "数据本体", required = true)
                                                @RequestBody UserModel model) throws Exception {
        model.setUuid(uuid);
        return service.updateById(model);
    }

    @ApiOperation(value = "分页条件查询")
    @RequestMapping(value = "/page/{pageNow}", method = RequestMethod.POST)
    public ResponseResult<PageInfo<UserModel>> findAll(@ApiParam(value = "当前页数", required = true)
                                                       @PathVariable("pageNow") int pageNow,
                                                       @ApiParam(value = "查询条件，字段至少传递一个，值可以空")
                                                       @RequestBody UserModel model) throws Exception {
        return service.findAll(pageNow, YamlPageUtils.getPageSize(), model);
    }
}
