package com.jqsoft.nursing.di.contract;

import com.jqsoft.nursing.bean.SchedulingBean;
import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.PersonCollectionBean;
import com.jqsoft.nursing.bean.grassroots_civil_administration.base.GCAHttpResultBaseBean;

import java.util.List;


public interface SchedulingFragmentContract {
    interface View{

        void onLoadListSuccess(HttpResultNurseBaseBean<List<SchedulingBean>> bean);
        void onLoadMoreListSuccess(HttpResultNurseBaseBean<List<SchedulingBean>> bean);

        void onLoadListFailure(String message, boolean isLoadMore);


    }

    interface  presenter{

    }
}
