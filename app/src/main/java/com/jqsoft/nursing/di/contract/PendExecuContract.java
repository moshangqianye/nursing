package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.PendExecuBeanList;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;

import java.util.List;


/**
 * Created by Administrator on 2017/5/21.
 */

public interface PendExecuContract {
    interface View{

        void onServicePackDetailSuccess(HttpResultBaseBean<List<PendExecuBeanList>> bean);

        void onLoadMoreServicePackDetailSuccess(HttpResultBaseBean<List<PendExecuBeanList>> bean);

        void onServicePackDetailFailure(String message);


        void onDeleteExecuServeritemSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);

        void onLoadDeleteExecuServeritemFailure(String message);

        void onServicePackDetailSuccess1(HttpResultBaseBean<List<PendExecuBeanList>> bean);

        void onServicePackDetailFailure1(String message);


    }

    interface  presenter{

    }
}
