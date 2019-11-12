package com.jqsoft.nursing.di.contract.nursing;

import com.jqsoft.nursing.bean.base.HttpResultNurseBaseBean;
import com.jqsoft.nursing.bean.nursing.DeanCockpitBedBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface DeanCockpitBedFragmentContract {
    interface View{

        void onLoadDeanCockpitBedListDataSuccess(HttpResultNurseBaseBean<List<DeanCockpitBedBean>> bean);

        void onLoadDeanCockpitBedListDataFailure(String message);



    }

    interface  presenter{

    }
}
