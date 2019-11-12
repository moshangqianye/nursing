//package com.jqsoft.nursing.util.qrcode;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//
//import com.google.zxing.Result;
//import com.sunshuai.android_zxing.CaptureActivity;
//
//public class ZxingActivity extends CaptureActivity {
//
//    @Override
//    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
//        Intent intent = new Intent();
//        intent.putExtra(IntentConstant.EXTRANAME_QR_CODE_TEXT, rawResult.getText());
//        this.setResult(IntentConstant.REQUESTCODE_SCAN_QR_CODE, intent);
//        finish();
//    }
//}