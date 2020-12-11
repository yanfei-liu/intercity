package com.ld.intercity.business.apply.controller;

import com.google.gson.Gson;
import com.ld.intercity.business.apply.model.ApplyModel;
import com.ld.intercity.business.apply.service.ApplyService;
import com.ld.intercity.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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

    @ApiOperation("后台审核申请页")
    @RequestMapping(value = "/Init")
    @ResponseBody
    public ModelAndView Init(){
        return new ModelAndView("/pages/apply/apply.html");
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
     * @param uuid
     * @return int
     */
    @ApiOperation("申请通过")
    @RequestMapping(value = "applyAdopt",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<String> applyAdopt(@RequestParam String uuid){
        ResponseResult<String> stringResponseResult = new ResponseResult<>();
        try {
            ApplyModel  byPassengerId = applyService.getByUuid(uuid);
            if (byPassengerId.getProgress()==0){
                byPassengerId.setProgress(1);
                applyService.update(byPassengerId);
                stringResponseResult.setSuccess(true);
                stringResponseResult.setMessage("审核通过");
            }else {
                stringResponseResult.setSuccess(false);
                stringResponseResult.setMessage("该审核操作已被完成");
            }
        } catch (Exception e) {
            e.printStackTrace();
            stringResponseResult.setSuccess(false);
            stringResponseResult.setMessage("审核操作出错，请稍后再试");
        }
        return stringResponseResult;
    }

    /**
     * 拒绝通过
     * @param uuid
     * @return int
     */
    @ApiOperation("拒绝通过")
    @RequestMapping(value = "applyRefuse",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<String> applyRefuse(@RequestParam String uuid){
        ResponseResult<String> stringResponseResult = new ResponseResult<>();
        try {
        ApplyModel byPassengerId = null;
            byPassengerId = applyService.getByUuid(uuid);
            if (byPassengerId.getProgress()==0){
                byPassengerId.setProgress(5);
                int update = applyService.update(byPassengerId);
                if (update == 1) {
                    stringResponseResult.setSuccess(true);
                    stringResponseResult.setMessage("成功拒绝通过");
                }else {
                    stringResponseResult.setSuccess(false);
                    stringResponseResult.setMessage("拒绝通过失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            stringResponseResult.setSuccess(false);
            stringResponseResult.setMessage("拒绝通过失败");
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


    @ApiOperation("后台根据参数查询司机申请")
    @RequestMapping(value = "findAllByParamBack",method = RequestMethod.GET)
    @ResponseBody
    public String findAllByParamBack(@RequestParam("val") String val,
                                     @RequestParam("val1") String val1,
                                     @RequestParam("val2") String val2,
                                     @RequestParam("limit")String limit,
                                     @RequestParam("offset")String offset,
                                     @RequestParam("page")String page){
        Gson gson = new Gson();
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        if (StringUtils.isNotBlank(val)||StringUtils.isNotBlank(val1)||StringUtils.isNotBlank(val2)){
            List<ApplyModel> allByParamBack = applyService.findAllByParamBack(val, val1, val2);
            int i = Integer.parseInt(offset);
            int i1 = Integer.parseInt(limit);
            if (i1>allByParamBack.size()){
                i1 = allByParamBack.size();
            }
            List<ApplyModel> applyModels = allByParamBack.subList(i, i1);
            stringObjectHashMap.put("total",allByParamBack.size());
            stringObjectHashMap.put("rows",applyModels);
        }else {
            stringObjectHashMap.put("total",0);
            stringObjectHashMap.put("rows",new ArrayList<>());
        }
        String s = gson.toJson(stringObjectHashMap);
        return s;
    }

    @ApiOperation("后台根据UUID查询司机申请")
    @RequestMapping(value = "getByUuid",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<ApplyModel> getByUuid(String uuid){
        ResponseResult<ApplyModel> applyModelResponseResult = new ResponseResult<>();
        try {
            ApplyModel byUuid = applyService.getByUuid(uuid);
            applyModelResponseResult.setSuccess(true);
            applyModelResponseResult.setData(byUuid);
        } catch (Exception e) {
            e.printStackTrace();
            applyModelResponseResult.setSuccess(false);
            applyModelResponseResult.setMessage("查询异常，请稍后再试");
        }
        return applyModelResponseResult;
    }
}
