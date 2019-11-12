package com.arcsoft.arcfacedemo.idcard;

public class CompareResult {
    private boolean isSuccess;
    private double result;
    private int errCode;

    public CompareResult() {
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    protected void setSuccess(boolean success) {
        this.isSuccess = success;
    }

    public double getResult() {
        return this.result;
    }

    protected void setResult(double result) {
        this.result = result;
    }

    public int getErrCode() {
        return this.errCode;
    }

    protected void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
