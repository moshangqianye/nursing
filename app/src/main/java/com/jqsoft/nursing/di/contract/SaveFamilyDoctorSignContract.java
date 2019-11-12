package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface SaveFamilyDoctorSignContract {
    interface View{

        void onLoginSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);

        void onLoginFailure(String message);
    }

    interface  presenter{

    }
}
