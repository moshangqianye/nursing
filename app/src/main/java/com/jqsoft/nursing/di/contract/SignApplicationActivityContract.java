package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.base.HttpResultEmptyBean;
import com.jqsoft.nursing.bean.response_new.IndexAndOnlineSignInitialData;

import java.util.List;


public interface SignApplicationActivityContract {
    interface View{

        void onLoadListSuccess(HttpResultBaseBean<List<IndexAndOnlineSignInitialData>> bean);
        void onLoadMoreListSuccess(HttpResultBaseBean<List<IndexAndOnlineSignInitialData>> bean);

        void onLoadListFailure(String message, boolean isLoadMore);

        void onCancelSignApplicationSuccess(HttpResultBaseBean<HttpResultEmptyBean> bean);
        void onCancelSignApplicationFailure(String message);

    }

    interface  presenter{

    }
}
