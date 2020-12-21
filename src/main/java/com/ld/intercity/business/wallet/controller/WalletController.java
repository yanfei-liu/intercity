package com.ld.intercity.business.wallet.controller;

import com.ld.intercity.business.wallet.model.WalletModel;
import com.ld.intercity.business.wallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/wallet")
@RestController
public class WalletController {
    @Autowired
    private WalletService walletService;

    @RequestMapping("/findByUserId")
    public WalletModel findByUserId(@RequestParam("userId") String userId){
        return walletService.getByUserId(userId);
    }
}
