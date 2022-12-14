package com.unicom.quantum.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class CardData implements Serializable {
    private int cardVersion;
    private byte[] cardData;
    private String macAddr;
    private String backPass;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getBackPass() {
        return backPass;
    }

    public void setBackPass(String backPass) {
        this.backPass = backPass;
    }
    public int getCardVersion() {
        return cardVersion;
    }

    public void setCardVersion(int cardVersion) {
        this.cardVersion = cardVersion;
    }

    public byte[] getCardData() {
        return cardData;
    }

    public void setCardData(byte[] cardData) {
        this.cardData = cardData;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }
}
