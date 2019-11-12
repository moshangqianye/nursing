package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.PersonSignAgreement;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface SignedAgreementContract {
    interface View {
        void onLoginSuccess(HttpResultBaseBean<PersonSignAgreement> bean);

        void onLoginFailure(String message);
    }

    interface presenter {

    }
}
