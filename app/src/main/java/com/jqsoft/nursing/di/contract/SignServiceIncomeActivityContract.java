package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.response_new.SignServiceIncomeResultBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface SignServiceIncomeActivityContract {
    interface View{

        void onLoadDataSuccess(HttpResultBaseBean<List<SignServiceIncomeResultBean>> bean);

        void onLoadDataFailure(String message);

    }

    interface  presenter{

    }
}
