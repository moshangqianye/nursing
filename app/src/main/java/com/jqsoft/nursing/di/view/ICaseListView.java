package com.jqsoft.nursing.di.view;

import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.CaseListBean;

import java.util.List;

/**
 * @author yedong
 * @date 2019/1/17
 * 病例列表接口
 */
public interface ICaseListView {

    /**
     * 获取病例记录c成功回调方法
     * @param bean
     */
    void onLoadCaseListSuccess(HttpResultNurseBaseBean<List<CaseListBean>> bean);

    /**
     * 获取病例记录失败回调方法
     * @param message
     * @param isLoadMore
     */
    void onLoadCaseListFail(String message, boolean isLoadMore);

}
