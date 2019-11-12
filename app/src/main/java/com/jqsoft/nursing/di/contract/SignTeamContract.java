package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.SignTeamBean;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface SignTeamContract {
    interface View{

        void onLoginSuccess(HttpResultBaseBean<SignTeamBean> bean);

        void onLoginFailure(String message);
    }

    interface  presenter{

    }
}
