package com.arcsoft.arcfacedemo.idcard;

import android.graphics.Rect;

public class DetectFaceResult {
    private int errCode;
    private Rect faceRect;

    public DetectFaceResult() {
    }

    public int getErrCode() {
        return this.errCode;
    }

    protected void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public Rect getFaceRect() {
        return this.faceRect;
    }

    protected void setFaceRect(Rect faceRect) {
        this.faceRect = faceRect;
    }
}
