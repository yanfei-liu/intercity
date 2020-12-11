package com.ld.intercity.business.apply.service.serviceImpl;

import com.ld.intercity.business.apply.mapper.ApplyMapper;
import com.ld.intercity.business.apply.model.ApplyModel;
import com.ld.intercity.business.apply.service.ApplyService;
import com.ld.intercity.business.order.model.OrderModel;
import com.ld.intercity.business.order.service.OrderService;
import com.ld.intercity.business.user.model.UserModel;
import com.ld.intercity.business.user.service.UserService;
import com.ld.intercity.business.wallet.model.WalletModel;
import com.ld.intercity.business.wallet.service.WalletService;
import com.ld.intercity.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private WalletService walletService;

    @Override
    @Transactional
    public ResponseResult<String> save(ApplyModel applyModel) {
        ResponseResult<String> stringResponseResult = new ResponseResult<>();
//        List<OrderModel> byKeHuUserId = orderService.getByKeHuUserId(applyModel.getPassengerId());
//        if (byKeHuUserId==null&&byKeHuUserId.size()==0){
            List<ApplyModel> byPassengerId = findByPassengerId(applyModel.getPassengerId());
            if (byPassengerId==null&&byPassengerId.size()==0){
                applyModel.setUuid(UUID.randomUUID().toString());
                int save = applyMapper.save(applyModel);
                if (save==1){
                    stringResponseResult.setSuccess(true);
                    stringResponseResult.setMessage("申请记录已受理");
                }else {
                    stringResponseResult.setSuccess(true);
                    stringResponseResult.setMessage("申请记录生成失败");
                }
            }else {
                int a = 0;
                for (ApplyModel applyModel1:byPassengerId){
                    if ("3".equals(applyModel1.getProgress())){
                        a++;
                    }
                }
                if (a == byPassengerId.size()){
                    applyModel.setUuid(UUID.randomUUID().toString());
                    int save = applyMapper.save(applyModel);
                    if (save==1){
                        stringResponseResult.setSuccess(true);
                        stringResponseResult.setMessage("申请记录已受理");
                    }else {
                        stringResponseResult.setSuccess(true);
                        stringResponseResult.setMessage("申请记录生成失败");
                    }
                }else {
                    stringResponseResult.setSuccess(false);
                    stringResponseResult.setMessage("已有申请记录");
                }
            }
//        }else {
//            stringResponseResult.setSuccess(false);
//            stringResponseResult.setMessage("存在未结算订单");
//        }
        return stringResponseResult;
    }

    @Override
    @Transactional
    public int del(String uuid) {
        return applyMapper.del(uuid);
    }

    @Override
    @Transactional
    public int delByPassengerId(String passengerId) {
        return applyMapper.delByPassengerId(passengerId);
    }

    @Override
    @Transactional
    public int update(ApplyModel applyModel) {
        try {
            ResponseResult<UserModel> byId = userService.getById(applyModel.getPassengerId());
            UserModel data = byId.getData();
            data.setIdentity("2");
            userService.updateById(data);
            //  生成司机钱包账户
            WalletModel walletModel = new WalletModel();
            walletModel.setUuid(UUID.randomUUID().toString());
            walletModel.setUserId(data.getUserId());
            walletModel.setWalletAmount("0");
            walletService.save(walletModel);
            return applyMapper.update(applyModel);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<ApplyModel> findByPassengerId(String passengerId) {
        return applyMapper.findByPassengerId(passengerId);
    }

    @Override
    public List<ApplyModel> findAllByType(String type) {
        return applyMapper.findAllByType(type);
    }

    @Override
    public List<ApplyModel> findAllByParamBack(String val, String val1, String val2) {
        return applyMapper.findAllByParamBack(val, val1, val2);
    }

    @Override
    public ApplyModel getByUuid(String uuid) throws Exception{
        ApplyModel byUuid = applyMapper.getByUuid(uuid);
        return byUuid;
    }
}
