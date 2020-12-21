package com.ld.intercity.business.wallet.service;

import com.ld.intercity.business.wallet.model.WalletModel;
import com.ld.intercity.utils.ResponseResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface WalletService {
    WalletModel getByUserId(String userId);

    int update(WalletModel walletModel);

    int save(WalletModel walletModel);
}
