package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.ProgressBean;
import com.jqsoft.nursing.bean.SocailHistoryDetailsBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;


public interface HandleProgressContract {
    interface View {

        void onLoadListSuccess(GCAHttpResultBaseBean<List<ProgressBean>> bean);

        void onLoadMoreListSuccess(GCAHttpResultBaseBean<List<ProgressBean>> bean);

        void onLoadListFailure(String message, boolean isLoadMore);

        void onLoadHistoryDataSuccess(GCAHttpResultBaseBean<List<SocailHistoryDetailsBean>> bean);

        void onLoadHisdataFailure(String message, boolean isLoadMore);


    }

    interface presenter {

    }
}
