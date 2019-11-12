package com.jqsoft.nursing.arcface;


import com.jqsoft.nursing.bean.ReadIdCardBean;

/**
 * @author yedong
 * @date 2019/8/30
 * 扫描读取身份证回调接口
 */
public interface IScanCardView {
    void onBackListener(ReadIdCardBean bean);
}
