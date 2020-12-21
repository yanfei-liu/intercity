package com.ld.intercity.business.wallet.model;

public class WalletRecordModel {
    private String uuid;
    private String walletId;
    private String createBy;
    private String createDate;
    private String amount;
    private String cardNumber;
    private String status;

    public WalletRecordModel() {
    }

    public WalletRecordModel(String uuid, String walletId, String createBy, String createDate, String amount, String cardNumber, String status) {
        this.uuid = uuid;
        this.walletId = walletId;
        this.createBy = createBy;
        this.createDate = createDate;
        this.amount = amount;
        this.cardNumber = cardNumber;
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
