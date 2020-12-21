package com.ld.intercity.business.wallet.controller;

import com.ld.intercity.business.wallet.model.WalletRecordModel;
import com.ld.intercity.business.wallet.service.WalletRecordService;
import com.ld.intercity.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Api(tags = "司机提现接口")
@Slf4j
@RequestMapping("/wallet/walletRecord")
@RestController
public class WalletRecordController {
    @Autowired
    private WalletRecordService walletRecordService;

    @RequestMapping("/Init")
    public ModelAndView Init(ModelAndView modelAndView){
        modelAndView.setViewName("");
        return modelAndView;
    }

    /**
     * 保存申请的提现记录
     * @param recordUserId  申请人ID
     * @param amount    提现金额
     * @param carNumber    银行卡号
     * @return
     */
    @ApiOperation(value = "保存申请的提现记录")
    @RequestMapping("/applyWalletRecord")
    public ResponseResult<String> applyWalletRecord(
            @RequestParam("recordUserId") String recordUserId,
            @RequestParam("amount") int amount,
            @RequestParam("carNumber")String carNumber){
        ResponseResult<String> stringResponseResult = new ResponseResult<>();
        try {
            stringResponseResult = walletRecordService.saveWalletRecord(recordUserId, amount,carNumber);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            stringResponseResult.setSuccess(false);
            stringResponseResult.setMessage("提现记录生成失败，请稍后再试");
        }
        return stringResponseResult;
    }

    /**
     * 根据传入状态查询对应的提现记录
     * @param status    状态
     * @return
     */
    @ApiOperation(value = "根据传入状态查询对应的提现记录")
    @RequestMapping("/findAllWalletRecord")
    public ResponseResult<List<WalletRecordModel>> findAllWalletRecord(@RequestParam("status") String status){
        ResponseResult<List<WalletRecordModel>> listResponseResult = new ResponseResult<>();
        try {
            List<WalletRecordModel> allWalletRecord = walletRecordService.findAllWalletRecord(status);
            listResponseResult.setData(allWalletRecord);
            listResponseResult.setSuccess(true);
            listResponseResult.setMessage("成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            listResponseResult.setSuccess(false);
            listResponseResult.setMessage("查询失败，请稍后再试");
        }
        return listResponseResult;
    }

    @ApiOperation(value = "更改提现记录状态至已完成")
    @RequestMapping("/walletRecordComplete")
    public ResponseResult<String> walletRecordComplete(@RequestParam("walletRecordId") String walletRecordId){
        ResponseResult<String> stringResponseResult = new ResponseResult<>();
        int i = walletRecordService.walletRecordComplete(walletRecordId);
        if (i==1){
            stringResponseResult.setSuccess(true);
            stringResponseResult.setMessage("操作完成");
        }else {
            stringResponseResult.setSuccess(false);
            stringResponseResult.setMessage("操作失败");
        }
        return stringResponseResult;
    }

    @ApiOperation(value = "根据提现记录ID查询")
    @RequestMapping("/getWalletRecordById")
    public ResponseResult<WalletRecordModel> getWalletRecordById(@RequestParam("walletRecordId") String walletRecordId){
        ResponseResult<WalletRecordModel> walletRecordModelResponseResult = new ResponseResult<>();
        WalletRecordModel walletRecordById = walletRecordService.getWalletRecordById(walletRecordId);
        if (walletRecordById!=null){
            walletRecordModelResponseResult.setSuccess(true);
            walletRecordModelResponseResult.setMessage("完成");
            walletRecordModelResponseResult.setData(walletRecordById);
        }else {
            walletRecordModelResponseResult.setSuccess(false);
            walletRecordModelResponseResult.setMessage("未查询到该提现记录");
        }
        return walletRecordModelResponseResult;
    }
}
