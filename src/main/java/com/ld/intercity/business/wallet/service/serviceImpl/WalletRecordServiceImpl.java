package com.ld.intercity.business.wallet.service.serviceImpl;

import com.ld.intercity.business.wallet.mapper.WalletRecordMapper;
import com.ld.intercity.business.wallet.model.WalletModel;
import com.ld.intercity.business.wallet.model.WalletRecordModel;
import com.ld.intercity.business.wallet.service.WalletRecordService;
import com.ld.intercity.business.wallet.service.WalletService;
import com.ld.intercity.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class WalletRecordServiceImpl implements WalletRecordService {
    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletRecordMapper walletRecordMapper;

    @Override
    @Transactional
    public ResponseResult<String> saveWalletRecord(String recordUserId, int amount,String carNumber) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ResponseResult<String> stringResponseResult = new ResponseResult<>();
        WalletModel byUserId = walletService.getByUserId(recordUserId);
        //  余额
        BigDecimal bigDecimal = new BigDecimal(byUserId.getWalletAmount());
        //  提现金额
        BigDecimal bigDecimal1 = new BigDecimal(Integer.toString(amount));
        if (bigDecimal.compareTo(bigDecimal1)>=0){
            BigDecimal subtract = bigDecimal.subtract(bigDecimal1);
            byUserId.setWalletAmount(subtract.toString());
            walletService.update(byUserId);
            WalletRecordModel walletRecordModel = new WalletRecordModel();
            walletRecordModel.setUuid(UUID.randomUUID().toString());
            walletRecordModel.setCreateBy(recordUserId);
            walletRecordModel.setCreateDate(simpleDateFormat.format(new Date()));
            walletRecordModel.setWalletId(byUserId.getUuid());
            walletRecordModel.setAmount(bigDecimal1.toString());
            walletRecordModel.setCardNumber(carNumber);
            walletRecordModel.setStatus("0");
            int i = walletRecordMapper.saveWalletRecord(walletRecordModel);
            stringResponseResult.setSuccess(true);
            stringResponseResult.setMessage("请耐心等待工作人员审核完毕");
        }else {
            stringResponseResult.setSuccess(false);
            stringResponseResult.setMessage("账户余额不足");
        }
        return stringResponseResult;
    }

    @Override
    public List<WalletRecordModel> findAllWalletRecord(String status) throws Exception {
        return findAllWalletRecord(status);
    }

    @Override
    public int walletRecordComplete(String walletRecordId) {
        return walletRecordMapper.walletRecordComplete(walletRecordId);
    }

    @Override
    public WalletRecordModel getWalletRecordById(String walletRecordId) {
        return walletRecordMapper.getWalletRecordById(walletRecordId);
    }
}
