package com.jqsoft.livebody_verify_lib.bean;

/**
 * Created by ${Jerry}
 * on 2019/3/25
 */
public class CardBean {
    private   String sCardNo;
    private String sPersonName;
    private String sNation;
    private String sAddress;

    public String getsCardNo() {
        return sCardNo;
    }

    public void setsCardNo(String sCardNo) {
        this.sCardNo = sCardNo;
    }

    public String getsPersonName() {
        return sPersonName;
    }

    public void setsPersonName(String sPersonName) {
        this.sPersonName = sPersonName;
    }

    public String getsNation() {
        return sNation;
    }

    public void setsNation(String sNation) {
        this.sNation = sNation;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }
}
