package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.response_new.SignServiceIncomeResultBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface SignServiceIncomeFragmentContract {
    interface View{

        void onLoadDataSuccess(HttpResultBaseBean<SignServiceIncomeResultBean> bean);

        void onLoadDataFailure(String message);

    }

    interface  presenter{

    }
}
