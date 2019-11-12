package com.jqsoft.nursing.di.contract.nursing;

import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.DeanCockpitElderBean;
import com.jqsoft.nursing.bean.nursing.IndexElderBean;
import com.jqsoft.nursing.bean.nursing.OrgnizationBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface IndexElderFragmentContract {
    interface View{

        void onLoadDeanCockpitElderListDataSuccess(HttpResultNurseBaseBean<IndexElderBean> bean);

        void onLoadDeanCockpitElderListDataFailure(String message);

        void onLoadOrgnizationListDataSuccess(HttpResultNurseBaseBean<List<OrgnizationBean>> bean);

        void onLoadOrgnizationListDataFailure(String message);


    }

    interface  presenter{

    }
}
