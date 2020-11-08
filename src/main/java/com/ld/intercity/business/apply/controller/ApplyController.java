package com.ld.intercity.business.apply.controller;

import com.google.gson.Gson;
import com.ld.intercity.business.apply.model.ApplyModel;
import com.ld.intercity.business.apply.service.ApplyService;
import com.ld.intercity.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@ApiOperation("申请司机接口")
@RequestMapping("/apply")
@RestController
public class ApplyController {
    @Autowired
    private ApplyService applyService;
    public String toJson(Object o){
        Gson gson = new Gson();
        String s = gson.toJson(o);
        return s;
    }
    /**
     * 保存提交的申请
     * @param applyModel
     * @return int
     */
    @ApiOperation("保存提交的申请")
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<String> save(@RequestBody ApplyModel applyModel){
        HashMap<String, String> map = new HashMap<>();
        ResponseResult<String> save = applyService.save(applyModel);
        return save;
    }

    /**
     * 删除申请中的某条记录
     * @param uuid
     * @return int
     */
    @ApiOperation("删除申请中的某条记录")
    @RequestMapping(value = "del",method = RequestMethod.GET)
    @ResponseBody
    public String del(@PathVariable String uuid){
        HashMap<String, String> map = new HashMap<>();
        int del = applyService.del(uuid);
        if (del==1){
            map.put("success","true");
        }else {
            map.put("success","false");
        }
        String s = toJson(map);
        return s;
    }

    /**
     * 删除某用户的申请
     * @param passengerId
     * @return int
     */
    @ApiOperation("删除某用户的申请")
    @RequestMapping(value = "delByPassengerId",method = RequestMethod.GET)
    @ResponseBody
    public String delByPassengerId(@PathVariable String passengerId){
        HashMap<String, String> map = new HashMap<>();
        int del = applyService.delByPassengerId(passengerId);
        if (del==1){
            map.put("success","true");
        }else {
            map.put("success","false");
        }
        String s = toJson(map);
        return s;
    }

    /**
     * 申请通过
     * @param passengerId
     * @return int
     */
    @ApiOperation("申请通过")
    @RequestMapping(value = "applyAdopt",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<String> applyAdopt(@RequestParam String passengerId){
        ResponseResult<String> stringResponseResult = new ResponseResult<>();
        List<ApplyModel> byPassengerId = applyService.findByPassengerId(passengerId);
        for (ApplyModel app:byPassengerId){
            if (app.getProgress()==0){
                app.setProgress(1);
                applyService.update(app);
                stringResponseResult.setSuccess(true);
                stringResponseResult.setMessage("审核通过");
            }
        }
        return stringResponseResult;
    }

    /**
     * 修改提交的申请
     * @param applyModel
     * @return int
     */
    @ApiOperation("修改提交的申请")
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public String update(@RequestBody ApplyModel applyModel){
        HashMap<String, String> map = new HashMap<>();
        int upd = applyService.update(applyModel);
        if (upd==1){
            map.put("success","true");
        }else {
            map.put("success","false");
        }
        String s = toJson(map);
        return s;
    }

    /**
     * 查询某用户提交的申请
     * @param passengerId
     * @return int
     */
    @ApiOperation("查询某用户提交的申请")
    @RequestMapping(value = "findByPassengerId",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<List<ApplyModel>> findByPassengerId(@RequestParam("passengerId") String passengerId){
        ResponseResult<List<ApplyModel>> applyModelResponseResult = new ResponseResult<>();
        List<ApplyModel> byPassengerId = applyService.findByPassengerId(passengerId);
        if (byPassengerId!=null&&byPassengerId.size()>0){
            applyModelResponseResult.setSuccess(true);
            applyModelResponseResult.setData(byPassengerId);
            applyModelResponseResult.setMessage("查询成功");
        }else {
            applyModelResponseResult.setSuccess(false);
            applyModelResponseResult.setMessage("我查询到申请记录");
        }
        return applyModelResponseResult;
    }

    /**
     * 查询某状态的申请
     * @param type
     * @return int
     */
    @ApiOperation("查询某状态的申请")
    @RequestMapping(value = "findAllByType",method = RequestMethod.GET)
    @ResponseBody
    public String findAllByType(@RequestBody String type){
        List<ApplyModel> allByType = applyService.findAllByType(type);
        String s = toJson(allByType);
        return s;
    }
}
