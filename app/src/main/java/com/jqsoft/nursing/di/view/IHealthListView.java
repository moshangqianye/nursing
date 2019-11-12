package com.jqsoft.nursing.di.view;

import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.HealthListBean;

import java.util.List;

/**
 * @author yedong
 * @date 2019/1/16
 * 请求老人列表接口
 */
public interface IHealthListView {

    /**
     * 成功回调方法
     * @param bean
     */
    void onLoadHealthListSuccess(HttpResultNurseBaseBean<List<HealthListBean>> bean);

    /**
     * 失败回调方法
     * @param message
     * @param isLoadMore
     */
    void onLoadHealthListFail(String message, boolean isLoadMore);
}
