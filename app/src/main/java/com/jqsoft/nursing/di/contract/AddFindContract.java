package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.DetailFindBeans;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface AddFindContract {
    interface View{

        void onAddFindSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);
        void onAddFindFailure(String message);

        void onDetailFindSuccess(HttpResultBaseBean<DetailFindBeans> bean);
        void onDetailFindFailure(String message);


    }

    interface  presenter{

    }
}
