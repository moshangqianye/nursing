package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.SchedulingBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;

import java.util.List;


public interface SchedulingActivityContract {
    interface View{

        void onLoadListSuccess(HttpResultNurseBaseBean<List<SchedulingBean>> bean);
        void onLoadMoreListSuccess(HttpResultNurseBaseBean<List<SchedulingBean>> bean);

        void onLoadListFailure(String message, boolean isLoadMore);


    }

    interface  presenter{

    }
}
