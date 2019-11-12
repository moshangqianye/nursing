package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.SocailHistoryDetailsBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;


public interface HistoryDetailContract {
    interface View {

        void onLoadHistoryDataSuccess(GCAHttpResultBaseBean<List<SocailHistoryDetailsBean>> bean);

        void onLoadHisdataFailure(String message, boolean isLoadMore);


    }

    interface presenter {

    }
}
