package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.DemoCraticBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;


public interface DisPlayDemoCraticContract {
    interface View {

        void onLoadListSuccess(GCAHttpResultBaseBean<DemoCraticBaseBean> bean);

        void onLoadMoreListSuccess(GCAHttpResultBaseBean<DemoCraticBaseBean> bean);

        void onLoadListFailure(String message, boolean isLoadMore);

    }

    interface presenter {

    }
}
