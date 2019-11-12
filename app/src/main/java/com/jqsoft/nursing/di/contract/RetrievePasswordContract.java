package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface RetrievePasswordContract {
    interface View{

        void onCardNumberExistSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);

        void onCardNumberExistFailure(String message);

        void onRetrievePasswordSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);

        void onRetrievePasswordFailure(String msg);
    }

    interface  presenter{

    }
}
