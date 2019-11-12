package com.jqsoft.nursing.di.contract.nursing;

import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.DeanCockpitElderBean;
import com.jqsoft.nursing.bean.nursing.OrgnizationBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface DeanCockpitElderFragmentContract {
    interface View{

        void onLoadDeanCockpitElderListDataSuccess(HttpResultNurseBaseBean<List<DeanCockpitElderBean>> bean);

        void onLoadDeanCockpitElderListDataFailure(String message);

//        void onLoadOrgnizationListDataSuccess(HttpResultNurseBaseBean<List<OrgnizationBean>> bean);
//
//        void onLoadOrgnizationListDataFailure(String message);


    }

    interface  presenter{

    }
}
