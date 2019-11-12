package com.jqsoft.nursing.di.contract.nursing;

import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.DeanCockpitFinanceBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface DeanCockpitFinanceFragmentContract {
    interface View{

        void onLoadDeanCockpitFinanceListDataSuccess(HttpResultNurseBaseBean<List<DeanCockpitFinanceBean>> bean);

        void onLoadDeanCockpitFinanceListDataFailure(String message);



    }

    interface  presenter{

    }
}
