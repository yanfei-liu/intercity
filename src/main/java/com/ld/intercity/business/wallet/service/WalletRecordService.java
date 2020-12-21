package com.ld.intercity.business.wallet.service;

import com.ld.intercity.business.wallet.model.WalletRecordModel;
import com.ld.intercity.utils.ResponseResult;

import java.util.List;

public interface WalletRecordService {
    //   提现申请
    ResponseResult<String> saveWalletRecord(String recordUserId, int amount,String carNumber) throws Exception;
    //  查询指定状态下的全部提现记录
    List<WalletRecordModel> findAllWalletRecord(String status) throws Exception;

    int walletRecordComplete(String walletRecordId);

    WalletRecordModel getWalletRecordById(String walletRecordId);
}
