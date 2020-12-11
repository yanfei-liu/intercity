package com.ld.intercity.business.wallet.model;

/**
 * 钱包实体类
 */
public class WalletModel {
    private String uuid;
    private String userId;      //用户ID
    private String walletAmount;    //用户金额

    public WalletModel() {
    }

    public WalletModel(String uuid, String userId, String walletAmount) {
        this.uuid = uuid;
        this.userId = userId;
        this.walletAmount = walletAmount;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(String walletAmount) {
        this.walletAmount = walletAmount;
    }
}
