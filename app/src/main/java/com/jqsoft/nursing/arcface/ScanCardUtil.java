package com.jqsoft.nursing.arcface;


import com.jqsoft.nursing.bean.ReadIdCardBean;

/**
 * @author yedong
 * @date 2019/8/30
 * 扫描读取身份证回调工具类
 */
public class ScanCardUtil {

    private static IScanCardView mView;

    public static void setCallBack(IScanCardView view) {
        mView = view;
    }

    public static void doCallBackMethod(ReadIdCardBean bean){
        mView.onBackListener(bean);
    }

}
