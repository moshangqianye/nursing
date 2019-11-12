package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface DeleteFindContract {
    interface View{

        void onDeleteFindSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);
        void onDeleteFindFailure(String message);


    }

    interface  presenter{

    }
}
