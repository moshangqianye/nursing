package com.arcsoft.arcfacedemo.idcard;

public interface IdCardVerifyListener {
    void onPreviewResult(DetectFaceResult var1, byte[] var2, int var3, int var4);

    void onIdCardResult(DetectFaceResult var1, byte[] var2, int var3, int var4);
}
