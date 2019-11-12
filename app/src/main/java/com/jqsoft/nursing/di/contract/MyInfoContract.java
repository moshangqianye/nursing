package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;
import com.jqsoft.nursing.bean.response_new.LoginResultBean2;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface MyInfoContract {
    interface View{

//        void onLoadMyInfoSuccess(HttpResultBaseBean<MyInfoBean> bean);
//
//        void onLoadMyInfoFailure(String message);

        void onLoginSuccess(HttpResultBaseBean<LoginResultBean2> bean);

        void onLoginFailure(String message);

        void onUpdatePhoneSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);

        void onUpdatePhoneFailure(String message);

    }

    interface  presenter{

    }
}
