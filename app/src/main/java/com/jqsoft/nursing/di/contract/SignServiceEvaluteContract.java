package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.EvaluationInfos;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.response_new.LoginResultBean2;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface SignServiceEvaluteContract {
    interface View {

        void onLoginSuccess(HttpResultBaseBean<LoginResultBean2> bean);

        void onLoginFailure(String message);

        void onGetEvaluationInfos(HttpResultBaseBean<EvaluationInfos> bean);
    }

    interface presenter {

    }
}
