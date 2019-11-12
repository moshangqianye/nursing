package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.base.HttpResultBaseBean;
import com.jqsoft.nursing.bean.response_new.OnlineConsultationResultBean;

import java.util.List;


public interface OnlineConsultationActivityContract {
    interface View{

        void onLoadListSuccess(HttpResultBaseBean<List<OnlineConsultationResultBean>> bean);
        void onLoadMoreListSuccess(HttpResultBaseBean<List<OnlineConsultationResultBean>> bean);

        void onLoadListFailure(String message, boolean isLoadMore);

    }

    interface  presenter{

    }
}
