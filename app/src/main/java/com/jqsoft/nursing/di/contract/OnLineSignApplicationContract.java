package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.ClientPersonSignApply;
import com.jqsoft.nursing.bean.DoctorTeamInfo;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.response_new.LoginResultBean2;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface OnLineSignApplicationContract {
    interface View {
        void onLineApplicationListSuccess(HttpResultBaseBean<List<ClientPersonSignApply>> bean);

        void onLoadListSuccess(HttpResultBaseBean<List<DoctorTeamInfo>> bean);

        void onLoadMoreListSuccess(HttpResultBaseBean<List<DoctorTeamInfo>> bean);

        void onLoadListFailure(String message);
        void onLoginSuccess(HttpResultBaseBean<LoginResultBean2> bean);
        void onLoginFailure(String message);
    }



    interface presenter {

    }
}
