package com.jqsoft.nursing.bean;

/**
 * Created by Administrator on 2018/1/1.
 */

public class DetailHelpResult {
    private String itemName;
    private String moneyGrant;
    private String moneyDate;

    public DetailHelpResult() {
    }

    private String issuingAgency;

    public DetailHelpResult(String itemName, String moneyGrant, String moneyDate, String issuingAgency) {
        this.itemName = itemName;
        this.moneyGrant = moneyGrant;
        this.moneyDate = moneyDate;
        this.issuingAgency = issuingAgency;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMoneyGrant() {
        return moneyGrant;
    }

    public void setMoneyGrant(String moneyGrant) {
        this.moneyGrant = moneyGrant;
    }

    public String getMoneyDate() {
        return moneyDate;
    }

    public void setMoneyDate(String moneyDate) {
        this.moneyDate = moneyDate;
    }

    public String getIssuingAgency() {
        return issuingAgency;
    }

    public void setIssuingAgency(String issuingAgency) {
        this.issuingAgency = issuingAgency;
    }
}
