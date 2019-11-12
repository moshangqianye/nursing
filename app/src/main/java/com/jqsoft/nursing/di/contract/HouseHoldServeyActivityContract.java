package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.HouseHoldSurveyBean;
import com.jqsoft.nursing.bean.base.HttpResultBaseBean;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface HouseHoldServeyActivityContract {
    interface View {

        void onLoadListSuccess(HttpResultBaseBean<HouseHoldSurveyBean> bean);

        void onLoadMoreListSuccess(HttpResultBaseBean<HouseHoldSurveyBean> bean);

        void onLoadListFailure(String message, boolean isLoadMore);


    }

    interface presenter {

    }
}
