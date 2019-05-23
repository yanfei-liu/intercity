package com.ld.intercity.business.apply.controller;

import com.ld.intercity.business.apply.model.ApplyModel;
import com.ld.intercity.business.apply.service.ApplyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiOperation("申请司机接口")
@RequestMapping("/apply")
@RestController
public class ApplyController {
    @Autowired
    private ApplyService applyService;
    /**
     * 保存提交的申请
     * @param applyModel
     * @return int
     */
    @ApiOperation("保存提交的申请")
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseBody
    public int save(@RequestBody ApplyModel applyModel){
        return applyService.save(applyModel);
    }

    /**
     * 删除申请中的某条记录
     * @param uuid
     * @return int
     */
    @ApiOperation("删除申请中的某条记录")
    @RequestMapping(value = "del",method = RequestMethod.GET)
    @ResponseBody
    public int del(@PathVariable String uuid){
        return applyService.del(uuid);
    }

    /**
     * 删除某用户的申请
     * @param passengerId
     * @return int
     */
    @ApiOperation("删除某用户的申请")
    @RequestMapping(value = "delByPassengerId",method = RequestMethod.GET)
    @ResponseBody
    public int delByPassengerId(@PathVariable String passengerId){
        return applyService.delByPassengerId(passengerId);
    }

    /**
     * 申请通过
     * @param passengerId
     * @return int
     */
    @ApiOperation("申请通过")
    @RequestMapping(value = "applyAdopt",method = RequestMethod.GET)
    @ResponseBody
    public int applyAdopt(@PathVariable String passengerId){
        List<ApplyModel> byPassengerId = findByPassengerId(passengerId,"0");
        for (ApplyModel app:byPassengerId){

            applyService.update(app);
        }
        return 0;
    }

    /**
     * 修改提交的申请
     * @param applyModel
     * @return int
     */
    @ApiOperation("修改提交的申请")
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public int update(@RequestBody ApplyModel applyModel){
        return applyService.update(applyModel);
    }

    /**
     * 查询某用户提交的申请
     * @param passengerId
     * @return int
     */
    @ApiOperation("查询某用户提交的申请")
    @RequestMapping(value = "findByPassengerId",method = RequestMethod.POST)
    @ResponseBody
    public List<ApplyModel> findByPassengerId(@RequestParam("passengerId") String passengerId,@RequestParam("type")String type){
        return applyService.findByPassengerId(passengerId,type);
    }

    /**
     * 查询某状态的申请
     * @param type
     * @return int
     */
    @ApiOperation("查询某状态的申请")
    @RequestMapping(value = "findAllByType",method = RequestMethod.GET)
    @ResponseBody
    public List<ApplyModel> findAllByType(@RequestBody String type){
        return applyService.findAllByType(type);
    }
}
