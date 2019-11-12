package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface RegisterContract {
    interface View{

        void onRegisterSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);

        void onRegisterFailure(String message);
    }

    interface  presenter{

    }
}
