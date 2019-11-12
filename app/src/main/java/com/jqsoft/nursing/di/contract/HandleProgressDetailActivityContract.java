package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.ProgressDetailbean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;


public interface HandleProgressDetailActivityContract {
    interface View {

        void onLoadListSuccess(GCAHttpResultBaseBean<ProgressDetailbean> bean);

        void onLoadMoreListSuccess(GCAHttpResultBaseBean<ProgressDetailbean> bean);

        void onLoadListFailure(String message, boolean isLoadMore);

    }

    interface presenter {

    }
}
