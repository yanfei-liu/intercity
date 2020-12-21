package com.ld.intercity.business.wallet.service.serviceImpl;

import com.ld.intercity.business.wallet.mapper.WalletMapper;
import com.ld.intercity.business.wallet.model.WalletModel;
import com.ld.intercity.business.wallet.service.WalletService;
import com.ld.intercity.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletMapper walletMapper;
    @Override
    public WalletModel getByUserId(String userId) {
        return walletMapper.getByUserId(userId);
    }

    @Override
    @Transactional
    public int update(WalletModel walletModel) {
        return walletMapper.update(walletModel);
    }

    @Override
    @Transactional
    public int save(WalletModel walletModel) {
        return walletMapper.save(walletModel);
    }
}
